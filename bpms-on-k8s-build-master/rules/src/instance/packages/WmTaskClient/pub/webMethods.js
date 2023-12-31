
var row = "even";
var other = "odd";
var swap = "";

var path1 = "";
var menu1 = "";
var path1_class = "";


var normal = 1;
var error  = 2;
var serverkey = 4;


function w(text)
{
	document.write(text);
}


function pathInit(css_class)
{
	path1 = "";
	path1_class = css_class;
	
}

function pathAdd(text, url)
{
	path1 += text;
	path1 += " &gt; ";
}

function pathShow()
{
	w("<table width=100% >");
	w("<tr>");
	w("<td class=" + path1_class + ">");
	w(path1);
	w("</td></tr></table>");
}


function menuInit()
{
	menu1 = "";
	
}


function normalize(text)
{
        var newstring = "";
        for(var i = 0; i < text.length; i++){
	        if(text.substring(i,i+1) == "\uff11") newstring += "1"; 
	        else if(text.substring(i,i+1) == "\uff12") newstring += "2"; 
	        else if(text.substring(i,i+1) == "\uff13") newstring += "3"; 
	        else if(text.substring(i,i+1) == "\uff14") newstring += "4"; 
	        else if(text.substring(i,i+1) == "\uff15") newstring += "5"; 
	        else if(text.substring(i,i+1) == "\uff16") newstring += "6"; 
	        else if(text.substring(i,i+1) == "\uff17") newstring += "7"; 
	        else if(text.substring(i,i+1) == "\uff18") newstring += "8"; 
	        else if(text.substring(i,i+1) == "\uff19") newstring += "9"; 
	        else if(text.substring(i,i+1) == "\uff10") newstring += "0"; 
                else if(text.substring(i,i+1) == "\uff0e") newstring += ".";
                else if(text.substring(i,i+1) == "\u3002") newstring += ".";
                else newstring += text.substring(i,i+1);
	}
	return newstring;
}

function menuAdd(text, url, bonus_text)
{
	menu1 += "<li>";
	menu1 += "<a href=\"" + url + "\">" + text + "</a>";
	if (bonus_text)
	{
		menu1 += "<br>";
		menu1 += bonus_text;
	}
	menu1 += "</li>";

}


function menuShow()
{
	w("<table width=100% >");
	w("<tr>");
	w("<td>");  //class=\"submenu\"
	w("<ul>");
	w(menu1);
	w("</ul>");
	w("</td></tr></table>");

}



function messageInit()
{
	message1 = "";
	
}


function messageAdd(text, message_type)
{
	menu1 += text;

}


function messageShow()
{
	w("<table width=100% >");
	w("<tr>");
	w("<td class=\"message\">");
	w(menu1);
	w("</td>");
	w("</tr>");
	w("</table>");
}



function writeEditPass(mode, name, value)
{
	if(mode == 'edit')
	{
		w("<input type=\"password\" name=\""+name+"\" value=\""+value+"\">");
	}
	else
	{
		if(value == "")
			value = "unspecified";
		w(value);
	}
}

function writeEdit(mode, name, value)
{
	if(mode == 'edit')
	{
		w("<input type=\"text\" name=\""+name+"\" value=\""+value+"\">");
	}
	else
	{
		if(value == "")
			value = "unspecified";
		w(value);
	}
}

function writeMessage(msg)
{
	w("<TR><TD class=\"message\" colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;"+msg+"</TD></TR>");
}

function reloadTopFrame()
{
	parent.topmenu.location.replace("top.dsp");
}

function writeTD (c)
{
	w("<TD CLASS=\"");
	w(row);
	w(c);
	w("\">");
	return true;
}
function writeTDnowrap (c)
{
	w("<TD CLASS=\"");
	w(row);
	w(c);
	w("\" NOWRAP>");
	return true;
}

function writeTDspan(c,span)
{
	w("<TD CLASS=\"");
	w(row);
	w(c);
	w("\" COLSPAN=");
	w(span);
	w(">");
}

function writeTDrowspan(c,span)
{
	w("<TD CLASS=\"");
	w(row);
	w(c);
	w("\" ROWSPAN=");
	w(span);
	w(">");
}

function swapRows()
{
	swap = row;
	row = other;
	other = swap;
}

function resetRows()
{
	row = "even";
	other = "odd";
	swap = "";
}


function isNum(num) 
{
  	num = num.toString();

  	if (num.length == 0)
  	  	return false;

  	for (var i = 0; i < num.length; i++)
    	if (num.substring(i, i+1) < "0" || num.substring(i, i+1) > "9")
      		return false;

  	return true;
}


function verifyRequiredField(form, field)
{
	var data = document.forms[form][field].value;

	if (isblank(data))
	{
		document.forms[form][field].focus();
		return false;
	}
	return true;
 }

function verifyRequiredNonNegNumber(form, fieldName)
{
	var field = document.forms[form][fieldName];

	if (isblank(field.value))
	{
		field.focus();
		return false;
	}

	if ( !isNum(field.value))
	{
		field.focus();
		return false;
	}

	if ( field.value < 0)
	{
		field.focus();
		return false;
	}

	return true;
}



function verifyRequiredFieldList(form, fieldList)
{
	for (index in fieldList)
	{
	   if (!verifyRequiredField(form, fieldList[index]))
	   {
		  alert ("Error: The selected field requires valid data.");
		  return false;
	   }
	}
	return true;
 }

function verifyFieldsEqual(form, field1, field2)
{
	if (document.forms[form][field1].value != document.forms[form][field2].value)
	{
		alert("Error: Fields must have the same value.  Try typing them in again.");
		document.forms[form][field1].focus();
		return false;
	}
	return true;
}

function setNavigation(doc_url, help_url, is_doc)
{

	if(parent == null || parent.frames == null || parent.frames.menu == null || parent.frames.menu.document == null)
		return false;

	if(parent.frames.menu.moveArrow != null)
	{
		if(is_doc != null) parent.frames.menu.moveArrow(doc_url);
		else
		parent.frames.menu.moveArrow(doc_url+location.search);
  	}

	if(parent.frames.menu.document.forms["urlsaver"] != null && parent.frames.menu.document.forms["urlsaver"].helpURL != null)
		parent.frames.menu.document.forms["urlsaver"].helpURL.value = help_url;
  
        return true;
        
}  


function initMenu(firstImage)
{
	var previousMenuImage = document.images[firstImage];
	previousMenuImage.src="images/selectedarrow.gif";
	return true;
}

function swapColor(objName, label) {

       if (navigator.appName == 'Netscape') {}
       else {
	 var theObj = eval(objName);
     if(theObj.childNodes == null) return;
	 for(var i = 1; i < theObj.childNodes.length; i=i+4){
	   for(var j = 0; j < theObj.childNodes[1].childNodes.length; j++){
	     theObj.childNodes[i].childNodes[j].style.background='#f0f0e0';
	   }
	   if (label==true)
	     theObj.childNodes[i].firstChild.style.background='#f0f0e0';
	 }
       }   
}

function isblank(s)
{
    	for (var i=0; i<s.length; i++ ) {
        	var c  = s.charAt(i);
        	if ((c != ' ') && (c != '\n') && (c != '\t')) 
          		return false;
        }
        return true;
}

function isInteger(input)
{
      if ( isNaN(parseInt(input)) || (input.indexOf(".") >= 0) ||
         (parseInt(input) != input))
      {
	return false;
      }
      return true;
}

function isIntegerGreaterThan(input, comp)
{
      if (! isInteger(input))
      {
      	return false;
      }
      if (parseInt(input) <= parseInt(comp)) 
      {
	return false;
      }
      return true;
}

function updateTaskServerURL(){
	var taskServerElement = document.getElementById('task-server');
	var taskServerURLElement = document.getElementById('task-server-url');
	if(taskServerElement.value === 'INPROC') {
		taskServerURLElement.className = 'hidden';
	} else {
		taskServerURLElement.className = '';
	}
}

function setTaskServer(taskServer){
	if(taskServer){
		var taskServerElement = document.getElementById('task-server');
		taskServerElement.value = taskServer;
	}
}