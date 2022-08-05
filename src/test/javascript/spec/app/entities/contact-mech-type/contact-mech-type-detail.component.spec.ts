import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ContactMechTypeDetailComponent } from 'app/entities/contact-mech-type/contact-mech-type-detail.component';
import { ContactMechType } from 'app/shared/model/contact-mech-type.model';

describe('Component Tests', () => {
  describe('ContactMechType Management Detail Component', () => {
    let comp: ContactMechTypeDetailComponent;
    let fixture: ComponentFixture<ContactMechTypeDetailComponent>;
    const route = ({ data: of({ contactMechType: new ContactMechType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContactMechTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactMechTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactMechType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactMechType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
