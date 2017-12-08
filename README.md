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
String name = resule.child("a").attr("name").val();
// test
String test = resule.child("a").child("b").attr("test").val();
// demaxiya
String test1 = resule.child("a").childs("b").get(1).attr("test").val();
// hehehehe
String test2 = resule.child("a").firstChildText();
// <name>lalala</name>
```

```JAVA
String xml = "<a><b name=\"wow\"/></a>";
DocumentResult result = DocumentResult.loadString(xml);
String val = result.child("a").child("b").attr("name").val();
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
// 创建根节a点并添加属性name
DocumentTemplete templete = DocumentTemplete.create("a").attr("name", "test");
// 创建一个文本节点
DomElement text = DOM.text("<name>lalala</name>");
// 创建一个单标签节点，并为其添加属性 test
DomElement b = DOM.single("b").attr("test", "123456");
// 向根节点添加三个元素(一个文本节点和两个相同的单标签节点)
templete.child(text).child(b).child(b);
// 创建xml文本
String xml = templete.buildString();
// 打印
System.out.println(xml);
```