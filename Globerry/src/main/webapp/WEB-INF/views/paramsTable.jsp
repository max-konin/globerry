<%-- 
    Document   : paramsTable
    Created on : Jul 7, 2012, 5:37:37 PM
    Author     : Vadim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="panel">
    <a class="handle" href="#"><div id="divA" >Content <!-- Ссылка для пользователей с отключенным JavaScript --></div></a>
    <h3><span lang="ru">Заголовок</span></h3><br>
    <span lang="ru">
        <!--                    Таблица параметров-->
        <div id="paramsTable">
            <table width="300px" border="1">
                <!--        заголовок таблицы    -->
                <tr>
                    <td>Параметр</td>
                    <td>Значение</td>
                </tr>
                <!--        Слайдер    -->
                <tr>
                    <td>Слайдер</td>
                    <td>
                        <label for="cur-amount">Значение:</label> 
                        <input type="text" id="cur-amount"/> <br/>
                        <div class="mySliders">
                            <div id="slider-range-min" class="paramsTableSlider"></div>
                        </div>

                    </td>
                </tr>
                <!--        Цвет    -->
                <tr>
                    <td>Цвет</td>
                    <td>
                        <div id="myColorSelector1" class="colorSelector"><div style="background-color: #FF0000"></div></div>
                    </td>
                </tr>
                <!--        Параметр    -->
                <tr>
                    <td>Параметр</td>
                    <td>
                        <div id="param1"><input type="text" name="param1" value="val1" /></div>
                    </td>
                </tr>
            </table>
        </div>
    </span>
</div>