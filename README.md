```XML
<a name="test">
    <b test="demaxiya"/>
    <b test="hehehehe"/>
</a>
```


```Java
DocumentResult result = DocumentResult.load("D:\\xx.xml");
String name = resule.child("a").attr("name").val();
// print test
String test = resule.child("a").child("b").attr().val("test");
// print demaxiya
String test1 = resule.child("a").childs("b").get(1).attr("test").val();
// print hehehehe
```