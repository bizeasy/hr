import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PublicHolidaysDetailComponent } from 'app/entities/public-holidays/public-holidays-detail.component';
import { PublicHolidays } from 'app/shared/model/public-holidays.model';

describe('Component Tests', () => {
  describe('PublicHolidays Management Detail Component', () => {
    let comp: PublicHolidaysDetailComponent;
    let fixture: ComponentFixture<PublicHolidaysDetailComponent>;
    const route = ({ data: of({ publicHolidays: new PublicHolidays(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PublicHolidaysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PublicHolidaysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PublicHolidaysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load publicHolidays on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.publicHolidays).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
