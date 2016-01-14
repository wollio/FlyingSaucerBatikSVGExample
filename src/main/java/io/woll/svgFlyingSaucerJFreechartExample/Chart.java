/*
 * Copyright 2012 - 2015 by PostFinance Ltd - All rights reserved
 */
package io.woll.svgFlyingSaucerJFreechartExample;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.UnsupportedEncodingException;

import org.apache.batik.transcoder.print.PrintTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.PageBox;
import org.xhtmlrenderer.render.RenderingContext;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;

/**
 *
 * @author David Wollschlegel
 * @since 04.01.2016
 */
public class Chart implements ITextReplacedElement {

  private static final String XML_START = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
  private static final String XML_END = "";

  public static final String getChartSVG () {
    DefaultPieDataset dataset = new DefaultPieDataset( );
    dataset.setValue( "IPhone 5s" , new Double( 20 ) );
    dataset.setValue( "SamSung Grand" , new Double( 20 ) );
    dataset.setValue( "MotoG" , new Double( 40 ) );
    dataset.setValue( "Nokia Lumia" , new Double( 10 ) );

    JFreeChart chart = ChartFactory.createPieChart("title", dataset, true, true, false);


    SVGGraphics2D g2 = new SVGGraphics2D(500, 500);
    Rectangle r = new Rectangle(500, 500);
    chart.draw(g2, r);
    return g2.getSVGElement();
  }

  public static final Graphics2D getChartSVG2D() {
    DefaultPieDataset dataset = new DefaultPieDataset( );
    dataset.setValue( "IPhone 5s" , new Double( 20 ) );
    dataset.setValue( "SamSung Grand" , new Double( 20 ) );
    dataset.setValue( "MotoG" , new Double( 40 ) );
    dataset.setValue( "Nokia Lumia" , new Double( 10 ) );

    JFreeChart chart = ChartFactory.createPieChart("title", dataset, true, true, false);


    SVGGraphics2D g2 = new SVGGraphics2D(500, 500);
    Rectangle r = new Rectangle(500, 500);
    chart.draw(g2, r);
    return g2;
  }

  private int m_width;
  private int m_height;
  private Point m_location = new Point(0, 0);

  public Chart(int width, int height) {
    m_width = width;
    m_height = height;
  }

  @Override
  public void detach(LayoutContext c) {
  }

  @Override
  public int getBaseline() {
    return 0;
  }

  @Override
  public int getIntrinsicWidth() {
    return m_width;
  }

  @Override
  public int getIntrinsicHeight() {
    return m_height;
  }

  @Override
  public boolean hasBaseline() {
    return false;
  }

  @Override
  public boolean isRequiresInteractivePaint() {
    return false;
  }

  @Override
  public Point getLocation() {
    return m_location;
  }

  @Override
  public void setLocation(int x, int y) {
    m_location = new Point(x, y);
  }

  /**
   * Paints a JFreechart in a PDF.
   *
   * @param renderingContext
   * @param outputDevice
   * @param blockBox
   */
  public void paint(RenderingContext renderingContext, ITextOutputDevice outputDevice, BlockBox blockBox) {
    DefaultPieDataset dataset = new DefaultPieDataset( );
    dataset.setValue( "IPhone 5s" , new Double( 20 ) );
    dataset.setValue( "SamSung Grand" , new Double( 20 ) );
    dataset.setValue( "MotoG" , new Double( 40 ) );
    dataset.setValue( "Nokia Lumia" , new Double( 10 ) );

    JFreeChart chart = ChartFactory.createPieChart("title", dataset, true, true, false);

    long time = System.currentTimeMillis();
    PdfContentByte cb = outputDevice.getWriter().getDirectContent();
    float width = m_width / outputDevice.getDotsPerPoint();
    float height = m_height / outputDevice.getDotsPerPoint();

    PdfTemplate template = cb.createTemplate(width, height);
    Graphics2D zwei = template.createGraphics(width, height);
    Rectangle r = new Rectangle((int)width, (int)height);
    chart.draw(zwei, r);

    PrintTranscoder prm = new PrintTranscoder();
    PageFormat pg = new PageFormat();
    Paper pp = new Paper();
    pp.setSize(width, height);
    pp.setImageableArea(0, 0, width, height);
    pg.setPaper(pp);
    prm.print(zwei, pg, 0);
    zwei.dispose();

    PageBox page = renderingContext.getPage();
    float x = blockBox.getAbsX() + page.getMarginBorderPadding(renderingContext, CalculatedStyle.LEFT);
    float y = (page.getBottom() - (blockBox.getAbsY() + m_height)) + page.getMarginBorderPadding(
            renderingContext, CalculatedStyle.BOTTOM);
    x /= outputDevice.getDotsPerPoint();
    y /= outputDevice.getDotsPerPoint();

    cb.addTemplate(template, x, y);
    System.out.println(System.currentTimeMillis() - time);
  }
}
