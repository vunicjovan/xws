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
		return axios.get(`/ad/`).then((response) => {
			return response.data;
		});
	},

	async getDetailedAdvertisement(id) {
		try {
			const response = await axios.get(`/ad/${id}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	addRentingInterval(rentingInterval) {
		return axios.post("/ad/interval/", rentingInterval).then((response) => {
			return response.data;
		});
	},

	postComment(comment) {
		return axios.post("/ad/publisher/comment/", comment).then((response) => {
			return response.data;
		});
	},

	async updateAdvertisement(updatedAd) {
		try {
			const response = await axios.put(`/ad/`, updatedAd);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
