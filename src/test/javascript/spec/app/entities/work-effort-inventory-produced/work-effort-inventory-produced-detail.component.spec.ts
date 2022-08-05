import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortInventoryProducedDetailComponent } from 'app/entities/work-effort-inventory-produced/work-effort-inventory-produced-detail.component';
import { WorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';

describe('Component Tests', () => {
  describe('WorkEffortInventoryProduced Management Detail Component', () => {
    let comp: WorkEffortInventoryProducedDetailComponent;
    let fixture: ComponentFixture<WorkEffortInventoryProducedDetailComponent>;
    const route = ({ data: of({ workEffortInventoryProduced: new WorkEffortInventoryProduced(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortInventoryProducedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortInventoryProducedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortInventoryProducedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortInventoryProduced on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortInventoryProduced).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
