/*
 * Copyright 2013 - 2015 by PostFinance Ltd - All rights reserved
 */
package io.woll.svgFlyingSaucerJFreechartExample;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.print.PrintTranscoder;
import org.w3c.dom.Document;
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
 * @author David Wollschlegel, PF
 * @since 08.12.2015
 */
public class SVGReplacedElement implements ITextReplacedElement {

  private Point m_location = new Point(0, 0);
  private Document m_svg;
  private int m_cssWidth;
  private int m_cssHeight;
  private Graphics2D m_2d;

  public SVGReplacedElement(Document svg, int cssWidth, int cssHeight) {
    m_cssWidth = cssWidth;
    m_cssHeight = cssHeight;
    m_svg = svg;
  }

  public SVGReplacedElement(Graphics2D zweid, int cssWidth, int cssHeight) {
    m_cssWidth = cssWidth;
    m_cssHeight = cssHeight;
    m_2d = zweid;
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
    return m_cssWidth;
  }

  @Override
  public int getIntrinsicHeight() {
    return m_cssHeight;
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

  @Override
  public void paint(RenderingContext renderingContext, ITextOutputDevice outputDevice, BlockBox blockBox) {
    long time = System.currentTimeMillis();
    PdfContentByte cb = outputDevice.getWriter().getDirectContent();
    float width = m_cssWidth / outputDevice.getDotsPerPoint();
    float height = m_cssHeight / outputDevice.getDotsPerPoint();

    PdfTemplate template = cb.createTemplate(width, height);
    PrintTranscoder prm = new PrintTranscoder();
    TranscoderInput ti = new TranscoderInput(m_svg);
    prm.transcode(ti, null);
    PageFormat pg = new PageFormat();
    Paper pp = new Paper();
    pp.setSize(width, height);
    pp.setImageableArea(0, 0, width, height);
    pg.setPaper(pp);
    prm.print(m_2d, pg, 0);
    m_2d.dispose();

    PageBox page = renderingContext.getPage();
    float x = blockBox.getAbsX() + page.getMarginBorderPadding(renderingContext, CalculatedStyle.LEFT);
    float y = (page.getBottom() - (blockBox.getAbsY() + m_cssHeight)) + page.getMarginBorderPadding(
            renderingContext, CalculatedStyle.BOTTOM);
    x /= outputDevice.getDotsPerPoint();
    y /= outputDevice.getDotsPerPoint();

    cb.addTemplate(template, x, y);
    System.out.println(System.currentTimeMillis() - time);
  }
}
