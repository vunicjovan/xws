<template>
  <div>
      <form novalidate class="md-layout md-alignment-top-center" @submit.prevent="validateData">
          <md-card class="md-layout-item md-size-30 md-small-size-100">
              <md-card-header>
                  <div class="md-title">Change Password</div>
              </md-card-header>
              <md-card-content>
                  <div class="md-layout-item md-small-size-100">
                      <md-field :class="getValidationClass('currentPassword')">
                          <label for="currentPassword">Current password</label>
                          <md-input id="currentPassword" name="currentPassword" type="password" v-model="form.currentPassword" />
                          <span class="md-error" v-if="!$v.form.currentPassword.required">Password is required</span>
                          <span class="md-error" v-else-if="!$v.form.currentPassword.alpha">Invalid password format</span>
                          <span class="md-error" v-else-if="!$v.form.currentPassword.minLength">Password requires at least 8 characters</span>
                      </md-field>
                  </div>
                  <div class="md-layout-item md-small-size-100">
                      <md-field :class="getValidationClass('newPassword')">
                          <label for="newPassword">New password</label>
                          <md-input id="newPassword" name="newPassword" type="password" v-model="form.newPassword" />
                          <span class="md-error" v-if="!$v.form.newPassword.required">Password is required</span>
                          <span class="md-error" v-else-if="!$v.form.newPassword.alpha">Invalid password format</span>
                          <span class="md-error" v-else-if="!$v.form.newPassword.minLength">Password requires at least 8 characters</span>
                      </md-field>
                  </div>
                  <div class="md-layout-item md-small-size-100">
                      <md-field :class="getValidationClass('newRepeatPassword')">
                          <label for="newRepeatPassword">New password</label>
                          <md-input id="newRepeatPassword" name="newRepeatPassword" type="password" v-model="form.newRepeatPassword" />
                          <span class="md-error" v-if="!$v.form.newRepeatPassword.required">Password is required</span>
                          <span class="md-error" v-else-if="!$v.form.newRepeatPassword.alpha">Invalid password format</span>
                          <span class="md-error" v-else-if="!$v.form.newRepeatPassword.minLength">Password requires at least 8 characters</span>
                      </md-field>
                  </div>
              </md-card-content>
              <md-card-actions>
                  <md-button type="submit" class="md-primary" >Save changes</md-button>
              </md-card-actions>
          </md-card>
      </form>
  </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, minLength } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\W+)([a-zA-Z0-9!?#\s?]+)$/);

export default {
    name: "ChangePassword",
    mixins: [validationMixin],
    data() {
        return {
            form: {
                currentPassword: undefined,
                newPassword: undefined,
                newRepeatPassword: undefined,
            }
        }
    },
    methods: {
        getValidationClass(fieldName) {
			const field = this.$v.form[fieldName];

			if (field) {
				return {
					"md-invalid": field.$invalid && field.$dirty,
				};
			}
        },
        validateData() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				alert('Under construction')
			}
		},
    },
    validations: {
        form: {
            currentPassword: {
				required,
				sqli,
				minLength: minLength(8),
            },
            newPassword: {
				required,
				sqli,
				minLength: minLength(8),
			},
			newRepeatPassword: {
				required,
				sqli,
				minLength: minLength(8),
			},
        }
    }

}
</script>

<style>

</style>
