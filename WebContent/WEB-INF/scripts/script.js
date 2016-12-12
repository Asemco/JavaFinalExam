function getReply(url, id) {
	$(document).ready(function() {
		$.getJSON(url+id, function(data) {
			$("#reply"+id).append(data.postBlock.text + "<br />");
			$("#reply"+id).append("<i>Posted by: " + data.postBlock.postedBy + " on " + new Date(data.postBlock.datePosted).toString() + "</i>");
			
			$("#a"+id).attr("onclick", "");
		});
	});
}