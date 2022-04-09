

var vm = new Vue({
  el: '#app',
  data: {
    accessToken: null,
    preference: {
        creationFlag: false,
        sandboxLink: null,
        responses: [],
        creationResponse: null,
        newItem: {
            id: null,
            name: null,
            quantity: null,
            price: null
        },
        items: []
    },
    messages: {
        preference: {
            error: null
        }
    }
  },
  methods: {

    createPreference() {
        axios({
          method: 'post',
          url: '/api/create',
          responseType: 'json',
          data: {}
        })
        
          .then((response) => {
            console.log(response.status);
            console.log(response.data);
            this.preference.creationResponse = JSON.stringify(response.data,null,'\t');

			const mp = new MercadoPago("TEST-85a1332f-c459-4b6d-af17-317316626b13", {
			  locale: "es-AR",
			});
			
			// Inicializa el checkout
			mp.checkout({
			  preference: {
			    id: response.data.id,
			  },
			  render: {
			    container: ".cho-container", // Indica el nombre de la clase donde se mostrará el botón de pago
			    label: "Reservar", // Cambia el texto del botón de pago (opcional)
			  },
			});
			

          })
          .catch((error) => {
              if (error.response) {
                console.log('#ERROR: ', error.response.data);

              }
          });
          this.$forceUpdate();
          this.$nextTick(null);
    },

	buscarPago() {
		let idPago = document.getElementById("idPago");

        axios.get('https://api.mercadopago.com/v1/payments/' + idPago.value, {
		  headers: {
			'Access-Control-Allow-Credentials': true,
		    'Authorization': `Bearer TEST-7906613297114815-022012-784cb5d4681cda744ee7525de740d34f-250383898`,
			'Access-Control-Allow-Origin': `*`
		  }
		})
		.then((res) => {
		  console.log(res.data)
		})
		.catch((error) => {
		  console.error(error)
		})
    }

  }
});

