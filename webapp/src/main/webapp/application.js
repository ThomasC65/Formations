function RestService(baseUrl) {
	this.baseUrl = baseUrl
}

RestService.prototype = {
		getAllUser: function(callback) {
			$.get(this.baseUrl, callback)
		},
		
		getOneUser: function(idUser, callback) {
			$.get(`${this.baseUrl}/${idUser}`, callback)
		},
		
		createOrUpdateUser(user, callback) {
			$.ajax(this.baseUrl, {
				method: user.idUser<=0 ? 'put' : 'post',
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(user),
				success: callback
			})
		},
		
		deleteUser: function(idUser, callback) {
			$.ajax(`${this.baseUrl}/${idUser}`, {
				method: 'delete',
				success: () => callback()
			})
		}
}

let editedUser = null

function getTableRowForUser(user) {
	return `<tr data-id='${user.idUser}'>
				<td>${user.name}</td>
				<td>${user.lastname}</td>				
				<td>${user.agence}</td>
				<!-- <td><div class="buttonBar">
					<a class="editButton btn-floating yellow"><i class="material-icons">edit</i></a>
					<a class="deleteButton btn-floating red"><i class="material-icons">delete</i></a>
				</div></td>-->
			</tr>`
}

function showTable() {
	editedUser = null
	
	$('#crudFlipContainer').removeClass('flip')
}

function showCreateForm() {
	editedUser = null
	
	$('#name').val('')
	$('#lastname').val('')
	$('#agence').val('')

	
	$('#crudFlipContainer').addClass('flip')
	
	$('#name').focus()
}

function showEditUser(user) {
	editedUser = user
	
	$('#name').val(user.name)
	$('#lastname').val(user.lastname)
	$('#agence').val(user.agence)

	
	$('#crudFlipContainer').addClass('flip')
	
	$('#name').focus()
}

$(function() {
	let service = new RestService('rs/user')
	
	// Attention ce n'est pas forcément recommandé d'écrire de façon si
	// condensée !
	// Avez-vous des difficultés à lire cette ligne de code ?
	service.getAllUser( (users) => $('table.User').append( $(users.map(getTableRowForUser).join('')) ) )
	
	$("table.User").on("click", ".deleteButton", function() {
		let tableRow = $(this).closest("tr[data-id]")
		let id = tableRow.attr("data-id")
		
		service.delete(id, () => tableRow.remove())
	})
	
	$("table.User").on("click", ".editButton", function() {
		let tableRow = $(this).closest("tr[data-id]")
		let id = tableRow.attr("data-id")
		
		service.getOne(id, showEditForm)
	})
	
	$('#createButton').click(showCreateForm)
	
	$('#okButton').click(function() {
		user = editedUser
		if( !user )
			user = { id: -1 }
		
		user.name = $('#name').val()
		user.universe = $('#lastname').val()
		user.latitude = 1 * $('#agence').val()

		
		service.createOrUpdateUser(user, function(user) {
			let rowHtml = getTableRowForUser(user)
			
			let popRow = $(`table.User tr[data-id=${User.idUser}]`)
			if( popRow.length )
				popRow.replaceWith(rowHtml)
			else
				$('table.User').append( $(rowHtml) )
		})
		
		showTable()
	
	})
})
