import axios from "axios";

export default {
	postAdvertisment(advertisment) {
		return axios.post("/agent/ad/", advertisment).then((response) => {
			return response.data;
		});
	},

	postPhotos(id, photos) {
		const config = {
			headers: {
				"content-type": "multipart/form-data",
			},
		};

		return axios.post(`/agent/images/${id}/`, photos, config).then((response) => {
			return response.data;
		});
	},

	getAllAdvertisements() {
		return axios.get(`/view/`).then((response) => {
			return response.data;
		})
	},

	getDetailedAdvertisement(id) {
		return axios.get(`/view/${id}`).then((response) => {
			return response.data;
		})
	},

	addRentingInterval(rentingInterval) {
		return axios.post("/agent/interval/", rentingInterval).then((response) => {
			return response.data;
		})
	},

	getUserPublishedAdvertisements(ownerId) {
		return axios.get(`/view/agent/${ownerId}`).then((response) => {
			return response.data;
		});
	},


	getStatisticReport(ownerId) {
		return axios.get(`/agent/ad/${ownerId}/statistic`).then((response) => {
			return response.data;
		});
	},

	rateAdvertisement(adId, rating) {
		return axios.post(`/agent/ad/${adId}/vehicle/rate`, rating).then((response) => {
			return response.data;
		});
	},

	updateAdvertisement(adId, data) {
		return axios.put(`/agent/ad/${adId}`, data).then((response) => {
			return response.data;
		});
	},

	deleteAdvertisement(adId) {
		return axios.delete(`agent/ad/${adId}`).then((response) => {
			return response.data;
		});
	}
  
};
