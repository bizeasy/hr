import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ContactMechPurposeComponent } from 'app/entities/contact-mech-purpose/contact-mech-purpose.component';
import { ContactMechPurposeService } from 'app/entities/contact-mech-purpose/contact-mech-purpose.service';
import { ContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

describe('Component Tests', () => {
  describe('ContactMechPurpose Management Component', () => {
    let comp: ContactMechPurposeComponent;
    let fixture: ComponentFixture<ContactMechPurposeComponent>;
    let service: ContactMechPurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechPurposeComponent],
      })
        .overrideTemplate(ContactMechPurposeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechPurposeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechPurposeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactMechPurpose(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactMechPurposes && comp.contactMechPurposes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
