// SDK de Mercado Pago
import { mercadopago } from '../node_modules/mercadopago';

// Agrega credenciales
mercadopago.configure({
  access_token: "TEST-7906613297114815-022012-784cb5d4681cda744ee7525de740d34f-250383898",
});

// Crea un objeto de preferencia
let preference = {
    items: [
      {
        id: "1",
        title: "Mi producto",
        unit_price: 100,
        quantity: 1,
      },
    ],
  };
  
  mercadopago.preferences
    .create(preference)
    .then(function (response) {
      // En esta instancia deberás asignar el valor dentro de response.body.id por el ID de preferencia solicitado en el siguiente paso
    })
    .catch(function (error) {
      console.log(error);
    });