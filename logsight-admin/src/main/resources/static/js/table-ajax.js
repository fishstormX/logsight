function buildTable(element,data,fieldMap,attrmap,lengthList){
	let formBody =""
	for(item in data){
	    let i = 0
        formBody += '<tr data-id="'+data[item].id+'"'
        for(obj in attrmap){
            formBody= formBody+obj+ '="'+attrmap[obj]+ '" >'
        }
		for(field in fieldMap){
            if(lengthList!=null&&lengthList[i]>3&&data[item][fieldMap[field]].length>lengthList[i]){
                formBody +='<td class="shown-tooltip" data-toggle="tooltip" data-placement="top" data-original-title="'+data[item][fieldMap[field]]+'">'+data[item][fieldMap[field]].substring(0,lengthList[i]-1)+'...</td>'
            }else {
                formBody += '<td>' + data[item][fieldMap[field]] + '</td>'
            }
            i++
		}
        formBody +='</tr>'
		$(element).html(formBody)
    }
    $('.shown-tooltip').tooltip()
}