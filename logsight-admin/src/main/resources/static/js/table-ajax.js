function buildTable(element,data,fieldMap,attrmap){
	let formBody =""
	for(item in data){
        formBody += '<tr data-id="'+data[item].id+'"'
        for(obj in attrmap){
            formBody= formBody+obj+ '="'+attrmap[obj]+ '" >'
        }
		for(field in fieldMap){
            	formBody +='<td>'+data[item][fieldMap[field]]+'</td>'
                console.log(data[item][fieldMap[field]]);
		}
        formBody +='</tr>'
		$(element).html(formBody)
    }

}