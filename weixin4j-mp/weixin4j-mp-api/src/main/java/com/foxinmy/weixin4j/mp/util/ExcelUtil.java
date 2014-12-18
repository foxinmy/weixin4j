package com.foxinmy.weixin4j.mp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * excel工具类
 * 
 * @className ExcelUtil
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see
 */
public class ExcelUtil {

	/**
	 * 读取Excel2003,2007的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            输入流
	 * @param fileName
	 *            是2003还是2007 xls：2003，xlsx：2007
	 * @throws Exception
	 */
	public static String[][] read(File file) throws Exception {
		String fileExt = getExtension(file.getName());
		if (StringUtils.isNotBlank(fileExt)) {
			if (fileExt.toLowerCase().equals("xls")) {// 2003
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(file));
				// 打开HSSFWorkbook
				POIFSFileSystem fs = new POIFSFileSystem(in);
				Workbook wb = new HSSFWorkbook(fs);
				in.close();
				return readExcel(wb);
			} else if (fileExt.toLowerCase().equals("xlsx")) {// 2007
				Workbook wb = new XSSFWorkbook(new FileInputStream(file));
				return readExcel(wb);
			}
		}
		return null;
	}

	public static String[][] read4Special(File file, String fileName,
			int columnSize) throws Exception {
		String fileExt = getExtension(fileName);
		if (StringUtils.isNotBlank(fileExt)) {
			if (fileExt.toLowerCase().equals("xls")) {// 2003
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(file));
				// 打开HSSFWorkbook
				POIFSFileSystem fs = new POIFSFileSystem(in);
				Workbook wb = new HSSFWorkbook(fs);
				in.close();
				return readExcel4Special(wb, columnSize);
			} else if (fileExt.toLowerCase().equals("xlsx")) {// 2007
				Workbook wb = new XSSFWorkbook(new FileInputStream(file));
				return readExcel4Special(wb, columnSize);
			}
		}
		return null;
	}

	/**
	 * 读取Excel文件中的值
	 * 
	 * @param wb
	 * @return String[][]
	 */
	private static String[][] readExcel(Workbook wb) throws Exception {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		Cell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			Sheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss").format(date);
								} else {
									value = "";
								}
							} else {
								value = getRightStr(cell.getNumericCellValue()
										+ "");
								// value =
								// String.valueOf(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!("").equals(cell.getStringCellValue())) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							value = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "true"
									: "false");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = result.get(i);
		}
		return returnArray;
	}

	/*
	 * 读取excel数据
	 */
	private static String[][] readExcel4Special(Workbook wb, int columnSize)
			throws Exception {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		Cell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			Sheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[columnSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex < columnSize; columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss").format(date);
								} else {
									value = "";
								}
							} else {
								value = getRightStr(cell.getNumericCellValue()
										+ "");
								// value =
								// String.valueOf(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!("").equals(cell.getStringCellValue())) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							value = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "true"
									: "false");
							break;
						default:
							value = "";
						}
					} else {
						value = "";
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		String[][] returnArray = new String[result.size()][columnSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = result.get(i);
		}
		return returnArray;
	}

	/**
	 * double 类型数据转换
	 * 
	 * @param sNum
	 * @return
	 */
	private static String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");

		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.equals(".000000"))
			return String.valueOf(0d);
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}
		return resultStr;
	}

	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str要处理的字符串
	 * @return 处理后的字符串
	 */

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	private static String getExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return "";
	}

	public static void list2excel(HSSFWorkbook workbook, List<String> headers,
			Collection<?> datas) {

		JSONArray arrays = null; //

		String[] strings = null; //

		HSSFSheet sheet = null; // 工作表

		HSSFRow row = null; // 单元行

		HSSFCell cell = null; // 单元格

		HSSFRichTextString richText = null; // 单元格内容

		sheet = workbook.getSheetAt(0); // 创建表格

		int rowNum = sheet.getLastRowNum(); // 数据行号
		if (rowNum != 0) {
			rowNum++;
			sheet.createRow(rowNum);
			rowNum++;
		}

		HSSFCellStyle cellStyle = workbook.createCellStyle();// 创建单元格样式(用于表头)
		cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);// 设置单元格样式
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		HSSFFont f = workbook.createFont();
		f.setColor(HSSFColor.BLUE.index);

		HSSFFont font = workbook.createFont(); // 创建字体
		font.setColor(HSSFColor.VIOLET.index); // 设置字体属性
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		cellStyle.setFont(font); // 设置单元格的字体

		row = sheet.createRow(rowNum); // 创建表格标题行

		for (int i = 0; i < headers.size(); i++) {
			// 填充标题行的单元格数据
			cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			richText = new HSSFRichTextString(headers.get(i));
			cell.setCellValue(richText);
			sheet.autoSizeColumn(i);
		}
		rowNum++;
		HSSFCellStyle contentStyle = workbook.createCellStyle();// 创建单元格样式(用于表内容)
		contentStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		contentStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		font = workbook.createFont(); // 创建字体
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL); // 设置字体粗细
		contentStyle.setFont(font); // 设置单元格的字体样式

		int j = 0;
		for (Object obj : datas) {
			row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}
			if (obj instanceof JSONArray) {
				arrays = (JSONArray) obj;
				// 只能是 JSONObject
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject jsonObj = arrays.getJSONObject(i);
					cell = row.createCell(i);
					cell.setCellStyle(contentStyle);
					String val = jsonObj.getString(headers.get(i));
					richText = new HSSFRichTextString(val);
					richText.applyFont(f);
					cell.setCellValue(richText);
				}
				rowNum++;
			} else if (obj instanceof String[]) {
				strings = (String[]) obj;
				for (int i = 0; i < strings.length; i++) {
					cell = row.createCell(i);
					cell.setCellStyle(contentStyle);
					richText = new HSSFRichTextString(strings[i]);
					richText.applyFont(f);
					cell.setCellValue(richText);
				}
				rowNum++;
			} else {
				cell = row.createCell(j);
				cell.setCellStyle(contentStyle);
				richText = new HSSFRichTextString((String) obj);
				richText.applyFont(f);
				cell.setCellValue(richText);
				j++;
			}
		}
	}
}
