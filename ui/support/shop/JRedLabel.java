package ui.support.shop;

/**
 * @author 牟倪
 * @version 1.0
 * 淘宝风格红色按钮，需要自行添加鼠标特效
 */

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ui.support.MyType;
import ui.support.RoundBorder;

public class JRedLabel extends JLabel {
	public static int PIXEL = 1;

	public JRedLabel(String text, ImageIcon icon, int ALIGNMENT) {
		super(text, icon, ALIGNMENT);
		this.setBorder(new RoundBorder(0, MyType.redColor, PIXEL));
		this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
	}

	public JRedLabel(String text) {
		super(text);
		this.setBorder(new RoundBorder(0, MyType.redColor, PIXEL));
		this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		this.setHorizontalAlignment(JLabel.CENTER);
	}
}
