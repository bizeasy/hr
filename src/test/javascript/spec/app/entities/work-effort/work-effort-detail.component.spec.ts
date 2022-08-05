import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortDetailComponent } from 'app/entities/work-effort/work-effort-detail.component';
import { WorkEffort } from 'app/shared/model/work-effort.model';

describe('Component Tests', () => {
  describe('WorkEffort Management Detail Component', () => {
    let comp: WorkEffortDetailComponent;
    let fixture: ComponentFixture<WorkEffortDetailComponent>;
    const route = ({ data: of({ workEffort: new WorkEffort(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffort on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffort).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
