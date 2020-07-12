<template>
	<div v-if="getAdvertisement !== null" class="md-layout md-alignment-center-center">
		<flash-message class="myFlash"></flash-message>
		<md-dialog :md-active.sync="active">
			<md-dialog-title>Unavailable term</md-dialog-title>
			<md-dialog-content>
				<md-datepicker :class="{ 'md-invalid': $v.form.startDate.$error }" v-model="form.startDate">
					<label>Select start date</label>
					<span class="md-error" v-if="!$v.form.startDate.required">Start date is required</span>
				</md-datepicker>
				<md-datepicker :class="{ 'md-invalid': $v.form.endDate.$error }" v-model="form.endDate">
					<label>Select end date</label>
					<span class="md-error" v-if="!$v.form.endDate.required">End date is required</span>
				</md-datepicker>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="cancelDialog()">Cancel</md-button>
				<md-button class="md-primary" @click.prevent="validateDates">Add</md-button>
			</md-dialog-actions>
		</md-dialog>

		<md-dialog :md-active.sync="commentDialog">
			<md-dialog-title>Post comment</md-dialog-title>
			<md-dialog-content>
				<div class="md-layout">
					<div class="md-layout-item md-small-size-100">
						<md-field :class="{ 'md-invalid': $v.comment.title.$error }">
							<label>Title</label>
							<md-input type="text" v-model="comment.title"></md-input>
							<span class="md-error" v-if="!$v.comment.title.required">Title is required</span>
							<span class="md-error" v-if="!$v.comment.title.sqli">Title is not well formed</span>
						</md-field>
					</div>
				</div>
				<div class="md-layout">
					<div class="md-layout-item md-small-size-100">
						<md-field :class="{ 'md-invalid': $v.comment.content.$error }">
							<label>Content</label>
							<md-textarea type="text" v-model="comment.content" />
							<span class="md-error" v-if="!$v.comment.content.required">Content is required</span>
							<span class="md-error" v-if="!$v.comment.content.sqli">Content is not well formed</span>
						</md-field>
					</div>
				</div>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="cancelCommentDialog()">Cancel</md-button>
				<md-button class="md-primary" @click.prevent="validateComment">Add</md-button>
			</md-dialog-actions>
		</md-dialog>

		<md-card>
			<md-card-media>
				<hooper :centerMode="true" :itemsToShow="1" :infiniteScroll="true" :progress="true" :autoPlay="true" :playSpeed="2000">
					<slide v-for="photo in getAdvertisement.photos" :key="photo">
						<img class="images img1" :src="getPhotoURL(getAdvertisement.id, photo)" alt="Vehicle image" />
					</slide>
					<hooper-navigation slot="hooper-addons"></hooper-navigation>
					<hooper-pagination slot="hooper-addons"></hooper-pagination>
				</hooper>
			</md-card-media>

			<md-card-header>
				<div class="md-title">
					<b>{{ getAdvertisement.brand }} {{ getAdvertisement.model }}</b> ({{ getAdvertisement.vehicleClass }}) - Owned by
					<b>{{ getAdvertisement.owner }}</b>
				</div>
				<div class="md-subhead"><b>Location:</b> {{ getAdvertisement.location }}</div>
				<div class="md-subhead"><b>Price:</b> {{ getAdvertisement.price }} €</div>
				<div class="md-subhead"><b>Fuel:</b> {{ getAdvertisement.fuel }}</div>
				<div class="md-subhead"><b>Gearbox:</b> {{ getAdvertisement.gearbox }}</div>
			</md-card-header>

			<md-card-content>
				<div>
					<b>Advanced details:</b><br />
					<i>Vhicle Rating: {{ getAdvertisement.rating }}</i>
					<i>This vehicle has traveled {{ getAdvertisement.kmTraveled }} kilometers. </i>
					<i v-if="getAdvertisement.dailyLimit !== -1">It has a daily kilometer limit of {{ getAdvertisement.dailyLimit }} kilometers for crossing. </i>
					<i v-else>It does not have a daily kilometer limit. </i>
					<i>Number of children seats in this vehicle is {{ getAdvertisement.childSeatNumber }}. </i>
					<i v-if="getAdvertisement.android">It has android support.</i>
					<i v-else-if="!getAdvertisement.android">It doesn't have android support.</i>
					<i v-if="getAdvertisement.cdw"> This vehicle has Collision Damage Waiver policy.</i>
					<i v-else-if="!getAdvertisement.cdw">This vehicle doesn't have Collision Damage Waiver policy.</i>
				</div>
				<br />
				<div><b>Owner's description:</b><br />{{ getAdvertisement.description }}</div>
				<br />
			</md-card-content>

			<md-card-actions>
				<md-button
					v-if="getUser && getUser.id == getAdvertisement.ownerId"
					@click.native="$router.push('/ads/published/edit/' + getAdvertisement.id)"
					class="md-raised md-accent">Edit Details
        </md-button>
				
        <md-button
					v-if="getUser && getUser.id == getAdvertisement.ownerId && getAdvertisement.id == 1"
					@click.native="$router.push('/map/' + getAdvertisement.id)"
					class="md-raised md-accent">Show map
        </md-button>
				
        <md-button v-if="getUser && getUser.id == getAdvertisement.ownerId" 
          @click="setupEdit(getAdvertisement.id)" 
          class="md-raised md-accent">Edit availability
        </md-button>
        
        <md-button v-if="getUser && getUser.id == getAdvertisement.ownerId" 
          @click="deleteAd(getAdvertisement.id)" 
          class="md-raised md-accent">Delete ad
        </md-button>

		<md-button v-if="getUser && getUser.id == getAdvertisement.ownerId && getAdvertisement.comments.length !== 0" 
          @click="setupCommentPosting(getAdvertisement.id)" 
          class="md-raised md-accent">Post comment
        </md-button>
				
        <md-button 
          v-if="getUser && getUser.roles.includes('SIMPLE_USER') && getUser.id != getAdvertisement.ownerId" 
          @click="addCartItem(getAdvertisement.id)" 
          class="md-raised md-accent">Add to cart
        </md-button>
			</md-card-actions>
		</md-card>
		<md-card v-if="getAdvertisement.comments.length !== 0">
			<md-card-content>
				<div>
					<span class="md-headline">Comments</span>
				</div>

				<md-divider></md-divider>
				<div v-for="comment in getAdvertisement.comments" v-bind:key="comment.id">
					<div>
						<br />
						<span class="md-subheading">{{ comment.title }}</span>
						<div class="md-layout md-alignment-center md-subtitle">
							{{ comment.content }}
						</div>
						<br />
					</div>
					<md-divider></md-divider>
				</div>
			</md-card-content>
		</md-card>
	</div>
</template>

<script>
import { Hooper, Slide, Pagination as HooperPagination, Navigation as HooperNavigation } from "hooper";
import "hooper/dist/hooper.css";
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);


import { mapGetters, mapActions } from "vuex";
export default {
	name: "DetailedView",
	mixins: [validationMixin],
	components: {
		Hooper,
		Slide,
		HooperPagination,
		HooperNavigation,
	},
	data: function() {
		return {
			ad: null,
			active: false,
			commentDialog: false,
			form: {
				startDate: undefined,
				endDate: undefined,
				advertisementId: undefined,
			},
			comment: {
				title: undefined,
				content: undefined,
				userId: undefined,
				advertisementId: undefined,
				allowed: undefined,
			}
		};
	},
	mounted: function() {
		this.$store.dispatch("getDetailedAdvertisement", this.$route.params.id);
	},
	computed: {
		...mapGetters(["getAdvertisement", "getUser"]),
	},
	methods: {
		...mapActions(["getDetailedAdvertisement", "addCartItem", "addRentingInterval", "removeAdvertisement"]),
		getPhotoURL(advertisementId, photoName) {
			return `http://localhost:8089/agent/images/${advertisementId}/${photoName}/`;
		},

		addCartItem(advertisementId) {
			let payload = {
				cartId: this.getUser.id,
				cartItemId: advertisementId,
			};

			this.$store.dispatch("addCartItem", payload);
		},

		addUnabailableTerm() {
			this.$store.dispatch("addRentingInterval", this.form);
			this.active = false;
		},

		cancelDialog() {
			this.active = false;
			this.$v.form.$reset();
			this.form.startDate = undefined;
			this.form.endDate = undefined;
			this.form.advertisementId = undefined;
		},

		setupEdit(id) {
			this.form.advertisementId = id;
			this.active = true;
		},

		deleteAd(id) {
			this.$store.dispatch("removeAdvertisement", id)
				.then((data) => {	
					this.$router.push("/");
				})
				.catch((error) => this.flashWarning(error.message, {timeout: 2000}))
		},

		validateDates() {
			this.$v.form.$touch();

			if (!this.$v.form.$invalid) {
				this.showSearchDialog = false;
				this.addUnabailableTerm();
			}
		},

		setupCommentPosting(advertisementId) {
			this.comment.advertisementId = advertisementId;
			this.comment.userId = this.getUser.id;
			this.comment.allowed = true;
			this.commentDialog = true;
		},

		cancelCommentDialog() {
			this.commentDialog = false;
			this.$v.comment.$reset();
			this.comment.advertisementId = undefined;
			this.comment.title = undefined;
			this.comment.content = undefined;
		},

		addComment() {
			this.$store.dispatch("postAgentComment", this.comment)
				.then((data) => {	
					let comm = {
						id: data.id,
						title: data.title,
						content: data.content
					}

					this.$store.commit("addCommentToAdvertisementList", comm);

					this.$v.comment.$reset();
					this.comment.advertisementId = undefined;
					this.comment.title = undefined;
					this.comment.content = undefined;
					this.comment.allowed = undefined;
					this.comment.userId = undefined;
				})
				.catch((error) => this.flashWarning(error.message, {timeout: 2000}))
			this.commentDialog = false;
		},

		validateComment() {
			this.$v.comment.$touch();

			if (!this.$v.comment.$invalid) {
				this.addComment();
			}
		},
	},

	validations: {
		form: {
			startDate: {
				required,
			},
			endDate: {
				required,
			},
		},
		
		comment: {
			title: {
				required,
				sqli
			},
			content: {
				required,
				sqli
			},
		}
	},
};
</script>

<style>
.md-card {
	width: 70%;
	margin: 5%;
	padding: 1%;
	display: inline-block;
	vertical-align: top;
}

.images,
.hooper {
	width: 60%;
	height: 10%;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.img1 {
	border-radius: 15%;
}

.md-dialog .md-dialog-container {
	transform: none;
}
</style>
