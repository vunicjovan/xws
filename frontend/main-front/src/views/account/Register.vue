<template>
    <div>
        <form novalidate class="md-layout md-alignment-top-center" @submit.prevent="register()">
            <md-card class="md-layout-item md-size-30 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Register</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">First name</label>
                            <md-input type="text" name="first-name" id="first-name" v-model="form.firstName" autocomplete="given-name" />
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">Last name</label>
                            <md-input type="text" name="last-name" id="last-name" v-model="form.lastName" autocomplete="family-name" />
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">E-mail</label>
                            <md-input type="email" name="email" id="email" v-model="form.email" autocomplete="email" />
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">Password</label>
                            <md-input type="password" name="password" id="password" v-model="form.password"/>
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">Repeat Password</label>
                            <md-input type="password" name="password" id="password-repeat" v-model="form.passwordRepeat"/>
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-checkbox v-model="form.isAgent">Register as agent</md-checkbox>
                    </div>
                    <md-field v-if="form.isAgent">
                        <label for="company">Company</label>
                        <md-select name="company" id="company" v-model="form.companyBusinessNumber" md-dense>
                            <md-option v-for="company in companies" :key="company.businessNumber">{{ company.businessNumber }}</md-option>
                        </md-select>
                    </md-field>
                </md-card-content>
                <md-card-actions>
                    <md-button type="submit" class="md-primary">Register</md-button>
                </md-card-actions>
            </md-card>
        </form>
    </div>
</template>

<script>
export default {
    name: "Register",
    data: function() {
        return {
            companies: null,
            form : {
                firstName: undefined,
                lastName: undefined,
                email: undefined,
                password: undefined,
                passwordRepeat: undefined,
                isAgent: false,
                companyBusinessNumber: undefined,
            }
        }
    },
    mounted: function () {

        this.axios.post("http://localhost:8089/account/company/")
                      .then((response) => {
                          this.companies = response.data;
                      })
                      .catch(() => {
                          alert('Couldn\'t get registered companies!');
                      })
    },
    methods: {
        register: function() {
            alert('Blob')
            this.axios.post("http://localhost:8089/account/register", this.form)
                      .then(() => {
                          this.$router.push('/login');
                      })
                      .catch(() => {
                          alert('Registration has failed!');
                      })
        }
    }
    
}
</script>

<style scoped>
</style>