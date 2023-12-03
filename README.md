# OpenOffice API

An API to read and edit OpenAPI files with Java.

## What can this do ?

* Read text content of a LibreOffice file,
* Explore the content as a tree form,
* Modify and save, replace text content; i.e. **use this API as a template generator**,
* Extract images of a document,
* Modify the metadata of the document,
* Explore and change the style of elements.

## Examples

```java
public static class Demo {
    public static void main(String[] args) {
        // Parse the document
        OpenDocument doc = OpenDocumentParser.parse("F:/my_serious_files/template.odt");
        
        // Replace all occurrences of "{recipient}" by "M. Dupont
        doc.getContentRoot().replaceAll("{recipient}", "M. Dupont");
        
        // Change the amount of edit cycles
        doc.getMeta().setEditingCycles(1);
        
        // Save the template
        doc.applyAllChanges();
        doc.recreateFile("F:/my_serious_files/invoice_Dupont.odt");
        
        // Export images for some reason ?
        for(ImageNode image : doc.getAllOfType(ImageNode.class)) {
            image.exportImage(doc, "F:/random_files/" + image.getImageName());
        }
    }
}
```

## Extensions

You can easily extend the API with your own nodes.

```java
import fr.jamailun.ooapi.odt.*;
import fr.jamailun.ooapi.xml.XmlNode;

public class MyNode extends OdtNode {
    // register the class
    static {
        LibraryOdt.registerClass(MyNode.class);
    }
    
    // My node ...
    public MyNode(XmlNode xmlNode) {
        super(xmlNode);
        // ...
    }
}
```


