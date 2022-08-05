import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ContactMechTypeComponent } from 'app/entities/contact-mech-type/contact-mech-type.component';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';
import { ContactMechType } from 'app/shared/model/contact-mech-type.model';

describe('Component Tests', () => {
  describe('ContactMechType Management Component', () => {
    let comp: ContactMechTypeComponent;
    let fixture: ComponentFixture<ContactMechTypeComponent>;
    let service: ContactMechTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechTypeComponent],
      })
        .overrideTemplate(ContactMechTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactMechType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactMechTypes && comp.contactMechTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
