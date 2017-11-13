TextViewEllipses

TextView判断文字是否超长显示省略号。实现微信朋友圈文字折叠和展示全部的效果。

 ![img](https://github.com/githubanxianjie/mygif/blob/master/ezgif.com-gif-maker.gif)
 
textview内部肯定有算法判断了内容是否超出最大行数的，我们只要找到这个方法的返回值，根据返回值来判断是否超出 
TextView有个方法 getLayout(); 这个Layout对象有个方法：

int ellCount = tv_content.getLayout().getEllipsisCount(LINECOUNT - 1);

if ellCount 大于0 说明此行（LINECOUNT - 1）内容 超出最大限制，用点点点代替，反之不超过，

tempModel.hasEllipsis = (ellCount >= 1 || tv_content.getLineCount() > LINECOUNT);

再根据是否超出限制来说显示收起或者显示全部


 ![img](https://github.com/githubanxianjie/myResourceFiles/blob/master/1.PNG)
