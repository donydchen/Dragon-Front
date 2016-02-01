# Dragon-Front

THE COMMENT FOR *A COMPLETE FRONT END* OF THE DRAGON BOOK      
2015 SYSU COMPILER PROJECT 4     


##文件结构简介

* **bin:** 由eclipse自动生成的可执行文件
* **doc:** javadoc生成的注解文档，使用命令：`javadoc inter/*.java lexer/*.java main/*.java parser/*.java symbols/*.java -d ../doc -encoding UTF-8 -charset UTF-8`
* **ref:** 其中*CompilerAppendixA.pdf*是龙书附录A，而*documents.pdf*则是编译器源码的分析与注解
* **src:** 完整编译器前端的源码，含注释
* **uml:** 编译器前端类的类图，包含jpg档和uxf档，其中uxf档请使用[UMLet](http://www.umlet.com/)打开


##编译器识别语言的文法

```
program -> block
block -> { decls stmts }
decls -> decl decls | epsilon
decl -> type id
type -> basic dims
dims -> [ num ] dims | epsilon
stmts -> stmt stmts | epsilon

stmt -> ;
      | if ( bool ) stmt 
      | if ( bool ) stmt else stmt
      | while ( bool ) stmt
      | do stmt while ( bool ) ;
      | break ;
      | block
      | assign
assign -> id offset = bool ;
offset -> [ bool ] offset | epsilon

bool -> join | join || bool
join -> equality | equality && join
equality -> rel | rel == equality | rel != equality
rel -> expr 
     | expr < expr | expr <= expr 
     | expr >= expr | expr > expr
expr -> term | term + expr | term - expr
term -> unary | unary * term | unary / term
unary -> - unary | ! unary | factor
factor -> ( bool ) | num | real | true | false | id offset
```