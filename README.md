```XML
<a name="test">
    <![CDATA[<name>lalala</name>]]>
    <b test="demaxiya"/>
    <b test="hehehehe"/>
</a>
```


```Java
DocumentResult result = DocumentResult.load("D:\\xx.xml");
String name = resule.child("a").attr("name").val();
// test
String test = resule.child("a").child("b").attr("test").val();
// demaxiya
String test1 = resule.child("a").childs("b").get(1).attr("test").val();
// hehehehe
String test2 = resule.child("a").firstChildText();
// <name>lalala</name>
```