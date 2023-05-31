
## Project Descript And Instructions Of Use
> 1. Export the table structure of a database in mysql to an Excel table.
> 2. The export directory defaults to the current user's desktop.
> 3. POI API does not support Chinese auto-fit column width, export xlsx to set the adaptive column width by yourself.
> 4. After decompressing [TransformTableInformationToExcel.zip](TransformTableInformationToExcel.zip) in the project directory, configure the yml file and click the bat script to generate the xlsx file corresponding to the database.
> 5. The form is generated using the easyexcel tool, but due to the lack of understanding of the tool, the form style is formulated in the form of an interceptor. If there is a need to change the format of the excel-table, you can add the interceptor yourself (don’t forget to configure the order priority attribute).
> 6. The head in easyexcel is not used, so there is no need to make a head style.

## Supported database types
> 1. Mysql

## 项目描述和使用说明
> 1. 将mysql中某个数据库的表结构导出为Excel表格。
> 2. 导出目录默认为当前用户桌面。
> 3. POI API 不支持中文自动适配列宽，导出 xlsx 自行设置适配列宽。
> 4. 项目目录下 [TransformTableInformationToExcel.zip](TransformTableInformationToExcel.zip) 解压后，配置 yml 文件再点击 bat 脚本即可生成对应数据库的 xlsx 文件。
> 5. 表格生成使用 easyexcel 工具，但由于对该工具了解较浅，所以用拦截器的形式制定表格样式，对 excel 文档格式有更改需求，可自行添加拦截器(别忘了配置 order 优先级属性哦)。
> 6. 未使用 easyexcel 中的 head，无需制定 head 样式。

## 支持的数据库类型
> 1. Mysql