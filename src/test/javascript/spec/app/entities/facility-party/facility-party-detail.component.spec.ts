import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityPartyDetailComponent } from 'app/entities/facility-party/facility-party-detail.component';
import { FacilityParty } from 'app/shared/model/facility-party.model';

describe('Component Tests', () => {
  describe('FacilityParty Management Detail Component', () => {
    let comp: FacilityPartyDetailComponent;
    let fixture: ComponentFixture<FacilityPartyDetailComponent>;
    const route = ({ data: of({ facilityParty: new FacilityParty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityPartyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityPartyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityPartyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityParty on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityParty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
