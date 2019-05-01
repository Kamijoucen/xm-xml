文档解析
==================
```XML
<a name="test">
    <![CDATA[<name>lalala</name>]]>
    <b test="demaxiya"/>
    <b test="hehehehe"/>
</a>
```
```JAVA
DocumentResult result = DocumentResult.loadFile("D:\\xx.xml");
String name = resule.child("a").attr("name").getVal();
// test
String test = resule.child("a").child("b").attr("test").getVal();
// demaxiya
String test1 = resule.child("a").childs("b").get(1).attr("test").getVal();
// hehehehe
String test2 = resule.child("a").firstChildText();
// <name>lalala</name>
```

```JAVA
String xml = "<a><b name=\"wow\"/></a>";
DocumentResult result = DocumentResult.loadString(xml);
String val = result.child("a").child("b").attr("name").getVal();
// wow
```
文档创建
==================
创建如下文档:
```XML
<a name="test">
    <![CDATA[<name>lalala</name>]]>
    <b test="12345"/>
    <b test="12345"/>
</a>
```
```JAVA
DocumentTemplate template = DocumentTemplate.createDocument("a");
template.addAttr(new AttrNode("name", "test"));
template.addChild(new TextNode("<name>lalala</name>"));
TagBlockNode tag = new TagBlockNode("b");
tag.addAttr(new AttrNode("test", "12345"));
template.addChild(tag);
template.addChild(tag);
System.out.println(template.builder()); // 输出
System.out.println(template.formatBuilder()); // 格式化输出
```