var HuddleView = Backbone.View.extend({
	
	initialize : function() {
	},
	events:{
		"click #logOutBtn" : "reset"
	},
	reset : function()
	{
		//alert('h');
		Location.href = "login";
	}
});