/*
 * Copyright 2013 - 2015 by PostFinance Ltd - All rights reserved
 */
package io.woll.svgFlyingSaucerJFreechartExample;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

/**
 *
 * @author David Wollschlegel
 * @since 08.12.2015
 */
public class MediaReplacedElementFactory implements ReplacedElementFactory {

  private ReplacedElementFactory m_superFactory;

  public MediaReplacedElementFactory() {
  }

  public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback,
      int cssWidth, int cssHeight) {
    Element element = blockBox.getElement();
    if (element == null) {
      return null;
    }
    String nodeName = element.getNodeName();

    if ("chart".equals(nodeName)) {
      return new Chart(cssWidth, cssHeight);
    }
    return null;
  }

  @Override
  public void reset() {
    m_superFactory.reset();
  }

  public void setFormSubmissionListener(FormSubmissionListener listener) {
    m_superFactory.setFormSubmissionListener(listener);
  }

  public ReplacedElementFactory getSuperFactory() {
    return m_superFactory;
  }

  public void setSuperFactory(ReplacedElementFactory superFactory) {
    m_superFactory = superFactory;
  }

  public void remove(Element e) {
  }
}
