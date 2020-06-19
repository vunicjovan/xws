import axios from "axios";

export default {
	postAdvertisement(advertisement) {
		return axios.post("/ad/", advertisement).then((response) => {
			return response.data;
		});
	},

	postPhotos(id, photos) {
		const config = {
			headers: {
				"content-type": "multipart/form-data",
			},
		};

		return axios.post(`/image/${id}/`, photos, config).then((response) => {
			return response.data;
		});
	},

	getAllAdvertisements() {
		return axios.get(`/view/`).then((response) => {
			return response.data;
		});
	},

	getDetailedAdvertisement(id) {
		return axios.get(`/view/${id}`).then((response) => {
			return response.data;
		});
	},

	addRentingInterval(rentingInterval) {
		return axios.post("/interval/", rentingInterval).then((response) => {
			return response.data;
		});
	},

	postComment(comment) {
		return axios.post("/comment/", comment).then((response) => {
			return response.data;
		});
	},
};
