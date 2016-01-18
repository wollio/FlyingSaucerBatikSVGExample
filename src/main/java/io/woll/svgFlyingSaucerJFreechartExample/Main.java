package io.woll.svgFlyingSaucerJFreechartExample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author David Wollschlegel
 * @since 04.01.2016
 */
public class Main {

  //The template.. should be in an external file.
  private static final String TOTAL_EXAMPLE = "<!DOCTYPE html><html><head><title>WCM</title><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"></meta></head><body><chart style=\"display:block;width:500px;height:500px;\"></chart></body></html>";

  public static void main(String[] args) {
    MediaReplacedElementFactory mediaReplacedElementFactory = new MediaReplacedElementFactory();
    ITextRenderer renderer = new ITextRenderer();

    renderer.setDocumentFromString(TOTAL_EXAMPLE);

    mediaReplacedElementFactory.setSuperFactory(renderer.getSharedContext().getReplacedElementFactory());
    renderer.getSharedContext().setReplacedElementFactory(mediaReplacedElementFactory);

    File f = new File("pdf.pdf");
    OutputStream os;
    try {
      os = new FileOutputStream(f);
      renderer.layout();
      renderer.createPDF(os);
      os.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
