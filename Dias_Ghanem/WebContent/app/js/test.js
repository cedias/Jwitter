function loadXMLDoc()
{

var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  alert("welcome here 1"); 
  xmlhttp.open("POST","login?",true);
  xmlhttp.onreadystatechange = handleConnect;
  xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded ');
  xmlhttp.send("login=charles&password=3060501");
  alert("welcome here 2"); 
}

function handleConnect (){
	alert("welcome here 3"); 
	if (xmlhttp.readyState == 4){
		alert("welcome here 4"); 
		if (xmlhttp.status == 200){
			var rep = xmlhttp.responseXML.documentElement;
			alert(rep.getElemetsByTagName('status')[0].firstChild.data);
		}else{
			alert(xmlhttp.status+" Error during AJAX call");
		}
	}else{
		alert(xmlhttp.status+" help //");
	}
}