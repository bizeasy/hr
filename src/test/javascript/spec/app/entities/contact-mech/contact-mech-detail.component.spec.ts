import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ContactMechDetailComponent } from 'app/entities/contact-mech/contact-mech-detail.component';
import { ContactMech } from 'app/shared/model/contact-mech.model';

describe('Component Tests', () => {
  describe('ContactMech Management Detail Component', () => {
    let comp: ContactMechDetailComponent;
    let fixture: ComponentFixture<ContactMechDetailComponent>;
    const route = ({ data: of({ contactMech: new ContactMech(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContactMechDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactMechDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactMech on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactMech).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
