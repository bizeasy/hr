import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortStatusDetailComponent } from 'app/entities/work-effort-status/work-effort-status-detail.component';
import { WorkEffortStatus } from 'app/shared/model/work-effort-status.model';

describe('Component Tests', () => {
  describe('WorkEffortStatus Management Detail Component', () => {
    let comp: WorkEffortStatusDetailComponent;
    let fixture: ComponentFixture<WorkEffortStatusDetailComponent>;
    const route = ({ data: of({ workEffortStatus: new WorkEffortStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
