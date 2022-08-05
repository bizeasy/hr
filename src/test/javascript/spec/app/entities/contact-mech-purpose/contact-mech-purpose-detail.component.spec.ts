import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ContactMechPurposeDetailComponent } from 'app/entities/contact-mech-purpose/contact-mech-purpose-detail.component';
import { ContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

describe('Component Tests', () => {
  describe('ContactMechPurpose Management Detail Component', () => {
    let comp: ContactMechPurposeDetailComponent;
    let fixture: ComponentFixture<ContactMechPurposeDetailComponent>;
    const route = ({ data: of({ contactMechPurpose: new ContactMechPurpose(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechPurposeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContactMechPurposeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactMechPurposeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactMechPurpose on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactMechPurpose).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
