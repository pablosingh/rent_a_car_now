package com.digitalhouse.rentacarnow.config;

import com.digitalhouse.rentacarnow.entity.Policy;
import com.digitalhouse.rentacarnow.repository.PolicyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolicySeeder implements CommandLineRunner {

    private final PolicyRepository policyRepository;

    public PolicySeeder(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public void run(String... args) {
        if (policyRepository.count() > 0) {
            return;
        }
        List<Policy> defaults = List.of(
                create("Reservas y Tarifas", "reservas-tarifas", 1,
                        "Las reservas se realizan exclusivamente a través de la plataforma con autenticación. "
                                + "El precio se calcula por hora: se cobra la hora completa (ceil) según la tarifa por hora del vehículo. "
                                + "Toda reserva debe durar al menos 1 hora y sus horarios de inicio y fin deben caer en intervalos de 30 minutos (00 o 30). "
                                + "Si la duración supera las 48 horas, se aplica un 10% de descuento sobre el total. "
                                + "Entre reservas del mismo vehículo se respeta un buffer de 1 hora: no se puede solapar ni dejar menos de 60 minutos libres. "
                                + "El vehículo debe estar marcado como disponible; de lo contrario la reserva es rechazada. "
                                + "Los horarios se guardan en UTC y se muestran al usuario en hora local de Argentina (America/Argentina/Buenos_Aires)."),
                create("Cancelaciones y Reembolsos", "cancelaciones", 2,
                        "Podés cancelar tu reserva hasta 24 horas antes del inicio sin cargo. "
                                + "Cancelaciones con menos de 24 h de anticipación tienen una retención del 30% del total. "
                                + "Si no te presentás (no-show), se cobra el 100% de la reserva. "
                                + "Las cancelaciones por causa del proveedor (vehículo no disponible, falla mecánica) generan reembolso total sin retenciones. "
                                + "Para solicitar cancelación, contactanos desde tu panel en Mis Reservas o por los canales de contacto. "
                                + "Los reembolsos se acreditan dentro de los 5 a 10 días hábiles por el mismo medio de pago."),
                create("Uso del Vehículo y Responsabilidades", "uso-responsabilidades", 3,
                        "El conductor debe presentar licencia vigente, DNI y ser mayor de 21 años. "
                                + "El vehículo se entrega con tanque y condiciones documentadas con fotos; debe devolverse en el mismo estado, con tolerancia de 1 hora de buffer entre reservas. "
                                + "Está prohibido fumar, transportar sustancias ilegales o exceder la capacidad del vehículo. "
                                + "Multas, peajes y daños por mal uso son responsabilidad del titular de la reserva. "
                                + "Ante siniestro, el usuario debe dar aviso inmediato y realizar la denuncia correspondiente; el seguro básico está incluido y la franquicia varía por categoría. "
                                + "Kilometraje libre dentro del territorio argentino salvo que la categoría indique lo contrario."),
                create("Privacidad y Protección de Datos", "privacidad", 4,
                        "Tratamos tus datos (nombre, email, reservas, fotos de perfil) conforme a la Ley 25.326 de Protección de Datos Personales. "
                                + "Usamos tu información para gestionar reservas, autenticación JWT y comunicaciones del servicio. "
                                + "No compartimos datos con terceros salvo obligación legal o proveedores esenciales (pagos, hosting). "
                                + "Las contraseñas se almacenan hasheadas con BCrypt y nunca se exponen vía API. "
                                + "Podés solicitar acceso, rectificación o eliminación de tus datos escribiendo a privacidad@rentacarnow.com. "
                                + "Conservamos reservas y facturación por 5 años por obligaciones legales."),
                create("Propietarios, Categorías y Contenido", "propietarios-contenido", 5,
                        "Cada vehículo pertenece a un propietario (OWNER) verificado por un ADMIN; los empleados (EMPLOYEE) gestionan los autos de su OWNER. "
                                + "Las categorías (Económico, Compacto, Mediano, SUV, Pickup, Familiar, Premium, Utilitario, Eléctrico) son administradas por ADMIN; eliminar una categoría elimina en cascada todos sus vehículos, reservas, favoritos y archivos asociados. "
                                + "Las características (features) describen equipamiento y se gestionan por ADMIN. "
                                + "Las imágenes se almacenan en el servidor y se sirven desde /uploads; el contenido subido debe respetar derechos de terceros."),
                create("Contacto y Soporte", "contacto", 6,
                        "RentaCarNow — Tu viaje empieza aquí. "
                                + "Soporte: soporte@rentacarnow.com | Tel: +54 11 5555-0123 | Lun a Vie 9 a 18 h (America/Argentina/Buenos_Aires). "
                                + "Domicilio: Av. Corrientes 1234, CABA, Argentina. "
                                + "Para consultas sobre reservas, verificaciones de OWNER o reclamos, usá tu panel o escribinos por estos canales. "
                                + "Términos vigentes desde septiembre de 2026; nos reservamos el derecho de actualizar estas políticas con aviso previo en la plataforma.")
        );
        policyRepository.saveAll(defaults);
    }

    private Policy create(String title, String slug, int order, String content) {
        Policy p = new Policy();
        p.setTitle(title);
        p.setSlug(slug);
        p.setContent(content);
        p.setDisplayOrder(order);
        return p;
    }
}
