function isHalfWidthAlphanumeric(str){
	str = (str == null) ? "" : str;
	if(str.match(/^[A-Za-z0-9@.]*$/)){
	  return true;
	}
	return false;
}
function isMail(str){
	str = (str == null) ? "" : str;
	if(str.match(/^[A-Za-z0-9]*$/)){
	  return true;
	}
	return false;
}

var token =  document.getElementsByName('_csrf').item(0).content;
const axsiosConfig = {
	headers:{
		'X-XSRF-TOKEN' : token
	}
};
console.log('DEBUG:' + axsiosConfig);