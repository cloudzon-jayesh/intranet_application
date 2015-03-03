var userModel = Backbone.Model.extend({
        defaults: {
            Id: null,
            firstName: null,
            lastName: null,
            email: null,
            dob: null,
            joiningDate : null
        },
        initialize: function () {
           
        }
    });
var listOfUsers = Backbone.Collection.extend({
    model: userModel,
    url: 'rest/user/employeDetails.json'
});

var listOfUsersView = Backbone.View.extend({
    el: '.main-container',// The element we defined in HTML
    render: function () {
        var self = this;// Saving the scope object
        var _userList = new listOfUsers();
        _userList.fetch({
            success: function (data) {
                    
                var _userTemplate = _.template($('#employee_template').html(), { users: data.models });
                <% _.each(users, function(user) {%>
  				 <tr>
  				 <td>
           		<input type="checkbox" /></td>
       			
       			<td><%= user.get('firstName')%></td>
       			<td><%= user.get('lastName')%></td>
       			<td><%= user.get('email')%></td>
					<td><%= user.get('dob')%></td>
					<td><%= user.get('joiningDate')%></td>
      			 <td>
           		<a href="/#edit/<%= user.get('Id')%>" class="btn btn-primary">Edit</a></td>
           		</tr>
           		<%});%>
                self.$el.html(_userTemplate);
            }
        });
    }
});