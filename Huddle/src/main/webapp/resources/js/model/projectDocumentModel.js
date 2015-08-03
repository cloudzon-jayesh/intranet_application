var projectDocumentModel = Backbone.Model.extend(
{
	defaults : {
		"documentName" : '',
		"document":'',
	},

	validate : function(attrs) {
		var errors = [];
		if (!attrs.documentName) {
			errors.push({
				name : 'documentName',
				message : '* Please enter Document Name.'
			});
		}
		
		if ($("#documents").val() == '') {
			errors.push({
				name : 'documents',
				message : '* Please Select Document.'
			});
		}
		return errors.length > 0 ? errors : false;
	
	}
});