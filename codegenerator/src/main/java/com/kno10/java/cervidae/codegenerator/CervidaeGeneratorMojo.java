package com.kno10.java.cervidae.codegenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * @goal generate-sources
 * @phase generate-sources
 */
public class CervidaeGeneratorMojo extends AbstractMojo {
  /**
   * Sources
   * 
   * @parameter
   * @required
   */
  String sources;

  /**
   * @parameter default-value="target/generated-sources"
   * @required
   */
  File outputDirectory;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setIncludes(new String[] { "**/*.template" });
    scanner.setBasedir(sources);
    scanner.setCaseSensitive(false);
    scanner.scan();
    String[] files = scanner.getIncludedFiles();
    for (String file : files) {
      processTemplate(file);
    }
  }

  private void processTemplate(String specfn) throws MojoFailureException {
    File specf = new File(sources + "/" + specfn);
    long lastmod = specf.lastModified();
    try (FileInputStream in = new FileInputStream(specf)) {
      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLStreamReader parser = factory.createXMLStreamReader(in);
      while (true) {
        int event = parser.next();
        if (event == XMLStreamConstants.END_DOCUMENT) {
          parser.close();
          break;
        }
        if (event == XMLStreamConstants.START_ELEMENT) {
          if ("template".equals(parser.getLocalName())) {
            String outdir = new File(outputDirectory + "/" + specfn).getParent();
            loadTemplate(parser, lastmod).process(specf.getParent(), outdir);
          }
        }
      }
    } catch (IOException e) {
      throw new MojoFailureException("I/O error loading template.", e);
    } catch (XMLStreamException e) {
      throw new MojoFailureException("I/O error loading template.", e);
    }
  }

  private TemplateSpec loadTemplate(XMLStreamReader parser, long lastmod) throws XMLStreamException {
    TemplateSpec template = new TemplateSpec();
    template.lastmod = lastmod;
    while (true) {
      int event = parser.next();
      if (event == XMLStreamConstants.END_DOCUMENT) {
        throw new XMLStreamException("End of stream with open template specification?!?");
      }
      if (event == XMLStreamConstants.END_ELEMENT) {
        if ("template".equals(parser.getLocalName())) {
          return template;
        }
      }
      if (event == XMLStreamConstants.START_ELEMENT) {
        switch(parser.getLocalName()) {
        case "input": {
          String id = parser.getAttributeValue(null, "id");
          if (id == null) {
            id = "DEFAULT";
          }
          template.input.put(id, loadCharacters(parser));
          break;
        }
        case "output": {
          String id = parser.getAttributeValue(null, "id");
          if (id == null) {
            id = "DEFAULT";
          }
          template.output.put(id, loadCharacters(parser));
          break;
        }
        case "group": {
          template.groups.add(loadGroup(parser));
          break;
        }
        default:
          throw new XMLStreamException("Unexpected element: " + parser.getLocalName());
        }
      }
    }
  }

  private String loadCharacters(XMLStreamReader parser) throws XMLStreamException {
    String node = parser.getLocalName();
    int event = parser.next();
    StringBuilder buf = new StringBuilder();
    while (true) {
      if (event == XMLStreamConstants.CHARACTERS || event == XMLStreamConstants.ENTITY_REFERENCE) {
        buf.append(parser.getText());
      } else if (event == XMLStreamConstants.START_ELEMENT) {
        throw new XMLStreamException("Expected end element, got new element: <" + parser.getLocalName() + "... -- improperly escaped < in string?");
      } else if (event == XMLStreamConstants.COMMENT) {
        // do nothing
      } else { // usually: END_ELEMENT
        break;
      }
      event = parser.next();
    }
    if (event != XMLStreamConstants.END_ELEMENT) {
      throw new XMLStreamException("Expected end element, got event type " + event + " " + parser.getLocalName());
    }
    if (!node.equals(parser.getLocalName())) {
      throw new XMLStreamException("Expected end element for " + node + ", got end element " + parser.getLocalName());
    }
    return buf.toString();
  }

  private Group loadGroup(XMLStreamReader parser) throws XMLStreamException {
    Group group = new Group();
    group.link = parser.getAttributeValue(null, "link");
    while (true) {
      int event = parser.next();
      if (event == XMLStreamConstants.END_DOCUMENT) {
        throw new XMLStreamException("End of stream with open group specification?!?");
      }
      if (event == XMLStreamConstants.END_ELEMENT) {
        if ("group".equals(parser.getLocalName())) {
          return group;
        }
      }
      if (event == XMLStreamConstants.START_ELEMENT) {
        switch(parser.getLocalName()) {
        case "pattern":
          group.pattern = Pattern.compile(loadCharacters(parser));
          break;
        case "substitute":
          String id = parser.getAttributeValue(null, "id");
          if (id == null) {
            id = "DEFAULT";
          }
          group.substitutions.put(id, loadCharacters(parser));
          break;
        default:
          throw new XMLStreamException("Unexpected element: " + parser.getLocalName());
        }
      }
    }
  }

  /**
   * Template specification
   * 
   * @author Erich Schubert
   */
  private class TemplateSpec {
    public long lastmod;

    /**
     * File names.
     */
    HashMap<String, String> input = new HashMap<>(), output = new HashMap<>();

    /**
     * Substitution groups.
     */
    ArrayList<Group> groups = new ArrayList<>();

    void process(String templatedir, String outputdir) throws IOException {
      for (String key : input.keySet()) {
        String inf = input.get(key);
        String ouf = output.get(key);
        final String data;
        final File tf = new File(templatedir + "/" + inf);
        lastmod = Math.max(lastmod, tf.lastModified());
        try (FileInputStream in = new FileInputStream(tf)) {
          StringBuilder out = new StringBuilder();
          byte[] b = new byte[4096];
          for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
          }
          data = out.toString();
        }
        process(ouf, data, outputdir, 0, new HashMap<String, String>());
      }
    }

    private void process(String outname, String data, String outputdir, int i, Map<String, String> linkmap) throws IOException {
      if (i < groups.size()) {
        Group group = groups.get(i);
        String v = group.link != null ? linkmap.get(group.link) : null;
        if (v != null) {
          String newname = group.pattern.matcher(outname).replaceAll(group.getSubst(v));
          String newdata = group.pattern.matcher(data).replaceAll(group.getSubst(v));
          process(newname, newdata, outputdir, i + 1, linkmap);
        } else {
          for (String id : group.substitutions.keySet()) {
            if (group.link != null) {
              linkmap.put(group.link, id);
            }
            String newname = group.pattern.matcher(outname).replaceAll(group.getSubst(id));
            String newdata = group.pattern.matcher(data).replaceAll(group.getSubst(id));
            process(newname, newdata, outputdir, i + 1, linkmap);
            if (group.link != null) {
              linkmap.remove(group.link);
            }
          }
        }
      } else {
        final File oname = new File(outputdir + "/" + outname);
        if (!oname.canRead() || oname.lastModified() < lastmod) {
          System.out.println("Generating " + oname);
          // Ensure the directory exists:
          oname.getParentFile().mkdirs();
          try (FileOutputStream out = new FileOutputStream(oname)) {
            out.write(data.getBytes());
          }
          oname.setLastModified(lastmod);
        }
      }
    }
  }

  /**
   * A simple substitution group.
   * 
   * @author Erich Schubert
   */
  class Group {
    /**
     * Link ID.
     */
    String link;

    /**
     * Pattern matcher
     */
    Pattern pattern;

    /**
     * Substitution rules.
     */
    Map<String, String> substitutions = new HashMap<>();

    /**
     * Get a substitution pattern.
     * 
     * @param v group id.
     * @return Value
     */
    String getSubst(String v) {
      String r = substitutions.get(v);
      if (r == null) {
        return substitutions.get("DEFAULT");
      } else {
        return r;
      }
    }
  }
}
