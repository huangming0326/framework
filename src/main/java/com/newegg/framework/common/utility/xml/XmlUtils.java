package com.newegg.framework.common.utility.xml;


import org.springframework.util.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XmlUtils {

    private static XmlUtils xmlUtils = new XmlUtils();

    public static XmlUtils getInstance() {
        return xmlUtils;
    }

    public FileMergeResult MergeFiles(String[] files) {

        FileMergeResult mergeResult = new FileMergeResult(files);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.out.println(e.toString());
        }

        Document doc_main = null;
        Element root_main = null;
        if (files.length > 0) {
            String fileToMerge = "";
            for (int i = 0; i < files.length; i++) {
                fileToMerge = files[i];
                try {
                    if (doc_main == null) {
                        doc_main = db.parse(fileToMerge);
                        root_main = doc_main.getDocumentElement();
                    } else {
                        Document sub_document = null;
                        sub_document = db.parse(fileToMerge);
                        Element sub_element = sub_document.getDocumentElement();
                        NodeList sub_nodeList = sub_element.getChildNodes();
                        int sub_item_num = sub_nodeList.getLength();

                        for (int j = 1; j < sub_item_num; j = j + 2) {
                            //Element item = (Element) sub_nodeList.item(j);
                            //root_main.appendChild(item);
                            Element messageItem = (Element) sub_nodeList.item(i);
                            dupliate(doc_main, root_main, messageItem);
                        }
                    }
                    mergeResult.addFilesMerged(fileToMerge);
                } catch (SAXException e) {
                    System.out.println(e.toString());
                } catch (IOException e) {
                    System.out.println(e.toString());
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        try {
            mergeResult.setFileContentMerged(this.asXML(doc_main));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        return mergeResult;
    }

    private boolean dupliate(Document doc_dup, Element father, Element son) throws Exception {
        boolean isdone = false;
        Element parentElement = null;

        DuplicateChildElementObject childElementObject = isChildElement(father, son);
        if (!childElementObject.isNeedDuplicate()) {
            //节点相同不用合并
            isdone = true;
            parentElement = childElementObject.getElement();
        } else if (childElementObject.getElement() != null) {
            parentElement = childElementObject.getElement();
        } else {
            parentElement = father;
        }

        String son_name = son.getNodeName();
        Element subITEM = null;
        if (!isdone) {
            subITEM = doc_dup.createElement(son_name);
            // 复制节点的属性
            if (son.hasAttributes()) {
                NamedNodeMap attributes = son.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    String attribute_name = attributes.item(i).getNodeName();
                    String attribute_value = attributes.item(i).getNodeValue();
                    subITEM.setAttribute(attribute_name, attribute_value);
                }
            }
            //复制节点的值
            Node sonNode = son.getFirstChild();
            if (sonNode != null) {
                String sonContent = sonNode.getTextContent();
                if (!StringUtils.isEmpty(sonContent)) {
                    subITEM.setTextContent(sonContent);
                }
            }
            parentElement.appendChild(subITEM);
        } else {
            subITEM = parentElement;
        }

        // 复制子结点
        NodeList sub_messageItems = son.getChildNodes();
        int sub_item_number = sub_messageItems.getLength();
        if (sub_item_number < 2) {
            // 如果没有子节点,则返回
            isdone = true;
        } else {
            for (int j = 1; j < sub_item_number; j = j + 2) {
                // 如果有子节点,则递归调用本方法
                Element sub_messageItem = (Element) sub_messageItems.item(j);
                isdone = dupliate(doc_dup, subITEM, sub_messageItem);
            }
        }
        return isdone;
    }

    private DuplicateChildElementObject isChildElement(Element father, Element son) {
        DuplicateChildElementObject childElementObject = new DuplicateChildElementObject();
        NodeList messageItems = father.getChildNodes();
        int item_number = messageItems.getLength();
        //首先遍历所有节点，查找是否有完全相同的节点，防止同一节点已定义多次
        for (int i = 1; i < item_number; i = i + 2) {
            Element messageItem = (Element) messageItems.item(i);
            if (!messageItem.getNodeName().equals(son.getNodeName())) {
                continue;
            }
            if (messageItem.isEqualNode(son)) {//同时判断子节点是否一致
                childElementObject.setNeedDuplicate(false);
                childElementObject.setElement(messageItem);
                return childElementObject;
            }
        }
        for (int i = 1; i < item_number; i = i + 2) {
            Element messageItem = (Element) messageItems.item(i);
            //判断节点是否处于同一级别
            if (!messageItem.getNodeName().equals(son.getNodeName())) {
                continue;
            }
            if (isEqualNode(messageItem, son)) {//仅判断当前节点是否一致
                if (hasEqualAttributes(messageItem, son)) {//当前节点完全相同不需要合并
                    childElementObject.setNeedDuplicate(false);
                    childElementObject.setElement(messageItem);
                    return childElementObject;
                } else {//当前节点的属性不相同，需要合并
                    childElementObject.setNeedDuplicate(true);
                    childElementObject.setElement(father);
                    return childElementObject;
                }
            }
        }
        //目标文档该节点不存在，需要合并到目标文档中
        childElementObject.setNeedDuplicate(true);
        childElementObject.setElement(father);
        return childElementObject;
    }

    /**
     * 判断两个节点是否相同，未判断节点的属性
     *
     * @param arg0
     * @param arg
     * @return
     */
    private boolean isEqualNode(Node arg0, Node arg) {
        if (arg == arg0) {
            return true;
        }
        if (arg.getNodeType() != arg0.getNodeType()) {
            return false;
        }

        if (arg0.getNodeName() == null) {
            if (arg.getNodeName() != null) {
                return false;
            }
        } else if (!arg0.getNodeName().equals(arg.getNodeName())) {
            return false;
        }

        if (arg0.getLocalName() == null) {
            if (arg.getLocalName() != null) {
                return false;
            }
        } else if (!arg0.getLocalName().equals(arg.getLocalName())) {
            return false;
        }

        if (arg0.getNamespaceURI() == null) {
            if (arg.getNamespaceURI() != null) {
                return false;
            }
        } else if (!arg0.getNamespaceURI().equals(arg.getNamespaceURI())) {
            return false;
        }

        if (arg0.getPrefix() == null) {
            if (arg.getPrefix() != null) {
                return false;
            }
        } else if (!arg0.getPrefix().equals(arg.getPrefix())) {
            return false;
        }

        if (arg0.getNodeValue() == null) {
            if (arg.getNodeValue() != null) {
                return false;
            }
        } else if (!arg0.getNodeValue().equals(arg.getNodeValue())) {
            return false;
        }
        return true;
    }

    /**
     * 判断节点的属性是否相同
     *
     * @param arg0
     * @param arg
     * @return
     */
    private boolean hasEqualAttributes(Node arg0, Node arg) {

        NamedNodeMap map1 = arg0.getAttributes();
        NamedNodeMap map2 = arg.getAttributes();
        int len = map1.getLength();
        if (len != map2.getLength()) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            Node n1 = map1.item(i);
            if (n1.getNodeName() != null) {
                Node n2 = map2.getNamedItem(n1.getNodeName());
                if (n2 == null) {
                    return false;
                } else if (!n1.getNodeValue().equals(n2.getNodeValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    public String asXML(Document document) throws TransformerConfigurationException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        return output;
    }


    public class FileMergeResult {
        private List<String> filesToMerge = new ArrayList<String>();
        private List<String> filesMerged = new ArrayList<String>();
        private String fileContentMerged = "";
        //private KeyedCollection<FileMergeFailReason> fileMergeFailReasons = new KeyedCollection<FileMergeFailReason>();

        public FileMergeResult(String[] filesToMerge) {
            for (String file : filesToMerge) {
                this.filesToMerge.add(file);
            }
        }

        public FileMergeResult(List<String> filesToMerge) {
            this.filesToMerge = filesToMerge;
        }

        public void addFilesMerged(String filesMerged) {
            this.filesMerged.add(filesMerged);
        }

        public String getFileContentMerged() {
            return fileContentMerged;
        }

        public void setFileContentMerged(String fileContentMerged) {
            this.fileContentMerged = fileContentMerged;
        }

        public boolean HasFileMerged() {
            return this.filesMerged.size() > 0;
        }

    }


    /**
     * 复制子节点对象
     *
     * @author Administrator
     */
    public class DuplicateChildElementObject {
        private boolean needDuplicate = true;//记录该节点是否需要复制
        private Element element = null;//记录该节点的父节点

        public DuplicateChildElementObject() {
            super();
        }

        public boolean isNeedDuplicate() {
            return needDuplicate;
        }

        public void setNeedDuplicate(boolean needDuplicate) {
            this.needDuplicate = needDuplicate;
        }

        public Element getElement() {
            return element;
        }

        public void setElement(Element element) {
            this.element = element;
        }
    }

}
