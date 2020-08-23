<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  
  <body>
  	<form action="rec.jsp" method="post">
  		<table>
  			<tr>
  				<td>
  					用户名:
  				</td>
  				<td>
  					<input type="text" name="username">
  				</td>
  				<td>
  					&nbsp;&nbsp;（必填）
  				</td>
  			</tr>
  			<tr>
  				<td>
  					密  码:
  				</td>
  				<td>
  					<input type="password" name="password">
  				</td>
  				<td>
  					&nbsp;&nbsp;（必填）
  				</td>
  			</tr>
  			<tr>
  				<td>
  					确认密码:
  				</td>
  				<td>
  					<input type="password" name="repassword">
  				</td>
  			</tr>
  			<tr>
  				<td>
  					性  别:
  				</td>
  				<td>
  					<input type="radio" name="sex" value="男" checked="checked">男
  					<input type="radio" name="sex" value="女" >女
  				</td>
  			</tr>
  			
  			<tr>
  				<td>
  					爱  好:
  				</td>
  				<td>
  					<input type="checkbox" name="aihao"  checked="checked">计算机
  					<input type="checkbox" name="aihao"  > 音乐
  					<input type="checkbox" name="aihao"  checked="checked"> 体育
  					<input type="checkbox" name="aihao"  > 文学
  				</td>
  			</tr>
  			<tr>
  				<td>
  					星  座:
  				</td>
  				<td>
  					<select name="xingzuo">
  						<option selected="selected">巨蟹座</option>
  						<option >白羊座</option>
  						<option >金牛座</option>
  						<option >双子座</option>
  						<option >天枰座</option>
  					</select>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					个人简介:
  				</td>
  				<td>
  					<textarea rows="4" cols="40" name="intro"></textarea>
  				</td>
  			</tr>
  			<tr>
  				<td> 
  					<input type="submit" value="提交">
  					<input type="reset" value="重置">
  				</td>
  			</tr>
  		</table>
  	</form>
  
  </body>
</html>
