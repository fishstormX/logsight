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
window.dr=parseInt(getQueryString("dr",0))