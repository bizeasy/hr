import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TimeSheetDetailComponent } from 'app/entities/time-sheet/time-sheet-detail.component';
import { TimeSheet } from 'app/shared/model/time-sheet.model';

describe('Component Tests', () => {
  describe('TimeSheet Management Detail Component', () => {
    let comp: TimeSheetDetailComponent;
    let fixture: ComponentFixture<TimeSheetDetailComponent>;
    const route = ({ data: of({ timeSheet: new TimeSheet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TimeSheetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TimeSheetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TimeSheetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load timeSheet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.timeSheet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
