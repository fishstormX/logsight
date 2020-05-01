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
    },
    ajax: function(type,url,data,success,error){
        let resultS=null
        if(type==="GET"){
            $.ajax({
                type: type,
                url:  url,
                data:data,
                success: success,
                error:error,
                dataType: "json"
            });
        }else {
            $.ajax({
                type: type,
                url: url,
                contentType: "application/json",
                data: JSON.stringify(data),
                success: success,
                error: error,
                dataType: "json"
            });
        }
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

    if((typeof params=='string')&&params.constructor==String){
        href += "?" + params + "=" +  window.params.get(params)
    }else {
        for (index in params) {
            if (index == 0) {
                href += "?" + params[index] + "=" + window.params.get(params[index])
            } else if (window.params.get(params[index]) != undefined) {
                href += "&" + params[index] + "=" + window.params.get(params[index])
            }
        }
    }
   window.location.href=href
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