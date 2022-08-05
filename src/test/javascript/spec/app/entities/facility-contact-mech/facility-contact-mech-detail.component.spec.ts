import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityContactMechDetailComponent } from 'app/entities/facility-contact-mech/facility-contact-mech-detail.component';
import { FacilityContactMech } from 'app/shared/model/facility-contact-mech.model';

describe('Component Tests', () => {
  describe('FacilityContactMech Management Detail Component', () => {
    let comp: FacilityContactMechDetailComponent;
    let fixture: ComponentFixture<FacilityContactMechDetailComponent>;
    const route = ({ data: of({ facilityContactMech: new FacilityContactMech(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityContactMechDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityContactMechDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityContactMechDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityContactMech on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityContactMech).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
