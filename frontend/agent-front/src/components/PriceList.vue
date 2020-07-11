<template>
    <div class="md-layout md-alignment-top-center">
		<flash-message class="myFlash"></flash-message>
		<div class="md-layout-item md-size-80">
			<div class="outer">
				<h2>Global discount: {{ calculateDiscount() }}%</h2>
				<md-button class="md-primary" @click="change = true" v-if="!change">Change</md-button>
			</div>
			<div class="outer md-layout-item md-size-30" v-if="change">
				<md-field class="full">
					<label>Discount</label>
					<md-input v-model="discount" type="number" />
				</md-field>
				<md-button class="md-primary" @click="validateDiscount">send</md-button>
				<md-button class="md-primary" @click="change = false">cancel</md-button>
			</div>
		</div>
		<md-table v-model="pricelistItems" md-sort="name" md-sort-order="asc" md-card class="md-layout-item md-size-80 md-small-size-150">
			<md-table-toolbar>
				<h1 class="md-title md-toolbar-section-start">Price List</h1>
				<div class="md-toolbar-section-end">
					<md-button class="md-icon-button" @click="active = true">
						<md-icon class="fas fa-plus" />
					</md-button>
				</div>
			</md-table-toolbar>

			<md-table-empty-state md-label="No Price List Items" :md-description="`There are no price list items added, to add your fist click on button below`">
				<md-button class="md-primary md-raised" @click="active = true">Create New</md-button>
			</md-table-empty-state>

			<md-table-row v-if="pricelistItems.length !== 0" slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
				<md-table-cell md-label="Daily Price" md-sort-by="dailyPrice">{{ item.dailyPrice }}€</md-table-cell>
				<md-table-cell md-label="CDW Price" md-sort-by="cdwPrice">{{ item.cdwPrice }}€</md-table-cell>
				<md-table-cell md-label="Debt Price" md-sort-by="debtPrice">{{ item.debtPrice }}€</md-table-cell>
			</md-table-row>
		</md-table>

		<md-dialog :md-active.sync="active">
			<md-dialog-title>New Price List Item</md-dialog-title>
			<md-dialog-content>
				<form>
					<md-field :class="{ 'md-invalid': $v.pricelistForm.dailyPrice.$error }">
						<label>Daily price</label>
						<md-input type="number" v-model="pricelistForm.dailyPrice" step="0.01" />
						<span class="md-error" v-if="!$v.pricelistForm.dailyPrice.required">Daily price is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.pricelistForm.cdwPrice.$error }">
						<label>Collision damage waiver</label>
						<md-input type="number" v-model="pricelistForm.cdwPrice" step="0.01" />
						<span class="md-error" v-if="!$v.pricelistForm.cdwPrice.required">Collision damage waiver price is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.pricelistForm.debtPrice.$error }">
						<label>Debt price</label>
						<md-input type="number" v-model="pricelistForm.debtPrice" step="0.01" />
						<span class="md-error" v-if="!$v.pricelistForm.debtPrice.required">Debt price is required</span>
					</md-field>
				</form>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="active = false">Cancel</md-button>
				<md-button class="md-primary" @click.prevent="validatePriceListItem">Add</md-button>
			</md-dialog-actions>
		</md-dialog>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal } from "vuelidate/lib/validators";

export default {
	name: "PriceList",
	mixins: [validationMixin],
	data() {
		return {
			active: false,
			change: false,
			discount: undefined,
			pricelistForm: {
				dailyPrice: undefined,
				cdwPrice: undefined,
				debtPrice: undefined,
				creatorId: undefined,
			},
		};
	},
	mounted() {
		this.$store.dispatch("pullPriceList");
	},
	computed: {
		...mapGetters(["getPriceList", "getDiscount"]),
		pricelistItems: {
			get() {
				return this.getPriceList;
			},
			set(pricelist) {
				this.$store.commit("setPriceListItems", pricelist);
			},
		},
	},
	methods: {
		calculateDiscount() {
			return Math.round(this.getDiscount * 100);
		},
		validatePriceListItem() {
			this.$v.pricelistForm.$touch();

			if (!this.$v.pricelistForm.dailyPrice.$invalid && !this.$v.pricelistForm.cdwPrice.$invalid && !this.$v.pricelistForm.debtPrice.$invalid) {
				this.submitPriceListItem();
			}
		},
		submitPriceListItem() {
			this.pricelistForm.creatorId = 2;

			this.$store
				.dispatch("newPriceListItem", this.pricelistForm)
				.then((data) => {
					this.$v.$reset();
					this.pricelistForm.dailyPrice = undefined;
					this.pricelistForm.cdwPrice = undefined;
					this.pricelistForm.debtPrice = undefined;
					this.active = false;
				})
				.catch((error) => {
					this.active = false;
					this.flashWarning(error.message, {timeout: 3000})
				});
		},
		validateDiscount() {
			this.$v.discount.$touch();

			if (!this.$v.discount.$invalid) {
				this.submitDiscount();
			}
		},
		submitDiscount() {
			this.$store
				.dispatch("createDiscount", (this.discount / 100).toFixed(2))
				.then((data) => {
					this.$v.$reset();
					this.change = false;
					this.discount = undefined;
				})
				.catch((error) => this.flashWarning(error.message, {timeout: 2000}));
		},
	},
	validations: {
		discount: {
			required,
		},
		pricelistForm: {
			dailyPrice: {
				required,
			},
			cdwPrice: {
				required,
			},
			debtPrice: {
				required,
			},
		},
	},
};
</script>

<style scoped>
	.outer {
		display: flex;
		align-items: baseline;
	}

	.full {
		flex: 1;
	}
</style>
