#guava  学习demo  
### 纸上得来终觉浅，绝知此事要躬行
参考网站：https://github.com/google/guava/wiki
http://ifeve.com/google-guava/

### http://www.appinn.com/markdown/ markdown 语法参考

> ## 这是一个标题。
> 
> 1.   这是第一行列表项。
> 2.   这是第二行列表项。
> 
> 给出一些例子代码：
> 
>     return shell_exec("echo $input | $markdown_script");

1
> 1


无序列表使用星号、加号或是减号作为列表标记：
*   Red
*   Green
*   Blue
等同于：
+   Red
+   Green
+   Blue
也等同于：
-   Red
-   Green
-   Blue

有序列表则使用数字接着一个英文句点：
1.  Bird
2.  McHale
3.  Parish


如果要在列表项目内放进引用，那 > 就需要缩进：

*   A list item with a blockquote:

    > This is a blockquote
    > inside a list item.
    
    
    要在 Markdown 中建立代码区块很简单，只要简单地缩进 4 个空格或是 1 个制表符就可以，例如，下面的输入：
    
这是一个普通段落：
    
    这是一个代码区块。
    
分隔线

你可以在一行中用三个以上的星号、减号、底线来建立一个分隔线，行内不能有其他东西。你也可以在星号或是减号中间插入空格。下面每种写法都可以建立分隔线：

* * *

***

*****

- - -

---------------------------------------


