window.params = new Map();
console.log(window.params,"12")
var util={
    loadStyles: function (url) {
        let link = document.createElement("link");
        link.rel = "stylesheet"
        link.type = "text/css"
        link.href = url;
        let head = document.getElementsByTagName("head")[0]
        head.appendChild(link)
    }
}
function getQueryString(name,defaultValue)
{
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)")
    let r = window.location.search.substr(1).match(reg)
    if (r != null) {
        return unescape(r[2])
    }
    return defaultValue
}
window.params.set("dr",parseInt(getQueryString("dr",0)))
function hrefTo(params,path)
{
    let href = path
    if(null===path){
        href=window.location.pathname
    }
    if(href==="/"){
        href=""
    }
   for(index in params){
       if(index == 0 ){
           href+= "?"+params[index]+"="+window.params.get(params[index])
       }else if(window.params.get(params[index])!=undefined){
           href+= "&"+params[index]+"="+window.params.get(params[index])
       }
   }
    window.location.href=href
}