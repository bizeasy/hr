import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionTypeRateDetailComponent } from 'app/entities/empl-position-type-rate/empl-position-type-rate-detail.component';
import { EmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';

describe('Component Tests', () => {
  describe('EmplPositionTypeRate Management Detail Component', () => {
    let comp: EmplPositionTypeRateDetailComponent;
    let fixture: ComponentFixture<EmplPositionTypeRateDetailComponent>;
    const route = ({ data: of({ emplPositionTypeRate: new EmplPositionTypeRate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionTypeRateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplPositionTypeRateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionTypeRateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplPositionTypeRate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplPositionTypeRate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
